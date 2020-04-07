package com.example.Reservation;

import com.example.Reservation.Book.Book;
import com.example.Reservation.Book.BookRepository;
import com.example.Reservation.Member.Member;
import com.example.Reservation.Member.MemberRepository;
import com.example.Reservation.Reservation.BookMemberReservation;
import com.example.Reservation.Reservation.ReservationRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.spi.http.HttpHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	BookRepository bookRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	ReservationRepository reservationRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testBook() throws URISyntaxException {
		RestTemplate restTemplate=new RestTemplate();

		//testiranje book
		final String uri="http://localhost:"+randomServerPort+"/bookReservation";
		URI uriB=new URI(uri);

		String isbn="testiranje";
		Date datum=new Date();
		Boolean dostupna=true;
		String naziv="Nova knjiga";
		String zanr="Naucna literatura";
		String naziv_izdavaca="Sarajevo publishing";
		Boolean dozvoljena_u_biblio=false;
		Book book=new Book(isbn,datum,dostupna,naziv,zanr,naziv_izdavaca,dozvoljena_u_biblio);

		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Book> request = new HttpEntity<>(book,headers);

		//POST
		Integer id=0;
		try {
			ResponseEntity<Book> result = restTemplate.postForEntity(uriB,request, Book.class);
			id=result.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getIsbn(),isbn);
			Assert.assertEquals(result.getBody().getIsbn(),bookRepository.findById(id).get().getIsbn());
			Assert.assertEquals(result.getBody().getDatePublisher(),datum);
			Assert.assertEquals(result.getBody().getDatePublisher(),bookRepository.findById(id).get().getDatePublisher());
			Assert.assertEquals(result.getBody().getAvailable(),dostupna);
			Assert.assertEquals(result.getBody().getAvailable(),bookRepository.findById(id).get().getAvailable());
			Assert.assertEquals(result.getBody().getBookName(),naziv);
			Assert.assertEquals(result.getBody().getBookName(),bookRepository.findById(id).get().getBookName());
			Assert.assertEquals(result.getBody().getGenreName(),zanr);
			Assert.assertEquals(result.getBody().getGenreName(),bookRepository.findById(id).get().getGenreName());
			Assert.assertEquals(result.getBody().getPublisherName(),naziv_izdavaca);
			Assert.assertEquals(result.getBody().getPublisherName(),bookRepository.findById(id).get().getPublisherName());
			Assert.assertEquals(result.getBody().getLibraryReadOnly(),dozvoljena_u_biblio);
			Assert.assertEquals(result.getBody().getLibraryReadOnly(),bookRepository.findById(id).get().getLibraryReadOnly());
		}
		catch (HttpClientErrorException e){
			Assert.fail();
		}

		//POST ERROR
		Book knjigaError=new Book(null,null,null,null,null,null,null);
		request= new HttpEntity<>(knjigaError,headers);
		try {
			restTemplate.postForEntity(uriB,request,Book.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(400,ex.getRawStatusCode());
		}

		//GET BY ID
		String uriBbyID=uri+"/"+id;
		uriB=new URI(uriBbyID);

		try {
			ResponseEntity<Book> result=restTemplate.getForEntity(uriB,Book.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getIsbn(),isbn);
			Assert.assertEquals(result.getBody().getDatePublisher(),datum);
			Assert.assertEquals(result.getBody().getAvailable(),dostupna);
			Assert.assertEquals(result.getBody().getBookName(),naziv);
			Assert.assertEquals(result.getBody().getGenreName(),zanr);
			Assert.assertEquals(result.getBody().getPublisherName(),naziv_izdavaca);
			Assert.assertEquals(result.getBody().getLibraryReadOnly(),dozvoljena_u_biblio);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//UPDATE BY ID
		String updateNaziv="UnitTestUpdateReportType";
		Book updateKnjiga=new Book(isbn,datum,dostupna,updateNaziv,zanr,naziv_izdavaca,dozvoljena_u_biblio);
		request=new HttpEntity<>(updateKnjiga,headers);

		try {
			//System.out.println(uriB);
			restTemplate.put(uriB,request);
			Assert.assertEquals(bookRepository.findById(id).get().getBookName(),updateNaziv);
		}
		catch (HttpClientErrorException ex){
			Assert.fail();
		}

		//DELETE BY ID
		try {
			restTemplate.delete(uriB);
			List<Book> bookList=bookRepository.findAll();
			Boolean postoji=false;
			for (Book knj :bookList) {
				if (knj.getId()==id) {
					postoji=true;
					break;
				}
			}
			Assert.assertEquals(false,postoji);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//DELETE BY ID ERROR
		try {
			restTemplate.delete(uriB);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

		//GET BY ID ERROR
		try {
			ResponseEntity<Book> result=restTemplate.getForEntity(uriB,Book.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

	}
	@Test
	public void testMember() throws URISyntaxException {
		RestTemplate restTemplate=new RestTemplate();

		//testiranje member
		final String uri="http://localhost:"+randomServerPort+"/member";
		URI uriM=new URI(uri);

		Date datum=new Date();
		Boolean aktivan=true;
		String ime="imenic";
		String prezime="prezimenic";
		Date datum_rodjenja=new Date();
		String tip_clastva="bibliotekar";
		String uloga="zaposlenik";
		Member clan=new Member(datum,aktivan,ime,prezime,datum_rodjenja,tip_clastva,uloga);

		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Member> request = new HttpEntity<>(clan,headers);

		//POST
		Integer id=0;
		try {
			ResponseEntity<Member> result = restTemplate.postForEntity(uriM,request, Member.class);
			id=result.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getJoinDate(),datum);
			Assert.assertEquals(result.getBody().getJoinDate(),memberRepository.findById(id).get().getJoinDate());
			Assert.assertEquals(result.getBody().getActive(),aktivan);
			Assert.assertEquals(result.getBody().getActive(),memberRepository.findById(id).get().getActive());
			Assert.assertEquals(result.getBody().getFirstName(),ime);
			Assert.assertEquals(result.getBody().getFirstName(),memberRepository.findById(id).get().getFirstName());
			Assert.assertEquals(result.getBody().getLastName(),prezime);
			Assert.assertEquals(result.getBody().getLastName(),memberRepository.findById(id).get().getLastName());
			Assert.assertEquals(result.getBody().getBirthDate(),datum_rodjenja);
			Assert.assertEquals(result.getBody().getBirthDate(),memberRepository.findById(id).get().getBirthDate());
			Assert.assertEquals(result.getBody().getMembershipTypeName(),tip_clastva);
			Assert.assertEquals(result.getBody().getMembershipTypeName(),memberRepository.findById(id).get().getMembershipTypeName());
			Assert.assertEquals(result.getBody().getRoleName(),uloga);
			Assert.assertEquals(result.getBody().getRoleName(),memberRepository.findById(id).get().getRoleName());
		}
		catch (HttpClientErrorException e){
			Assert.fail();
		}

		//POST ERROR
		Member clanError=new Member(null,null,null,null,null,null,null);
		request= new HttpEntity<>(clanError,headers);
		try {
			restTemplate.postForEntity(uriM,request,Member.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(400,ex.getRawStatusCode());
		}

		//GET BY ID
		String uriBbyID=uri+"/"+id;
		uriM=new URI(uriBbyID);

		try {
			ResponseEntity<Member> result = restTemplate.getForEntity(uriM, Member.class);
			Assert.assertEquals(200, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getJoinDate(), datum);
			Assert.assertEquals(result.getBody().getActive(), aktivan);
			Assert.assertEquals(result.getBody().getFirstName(), ime);
			Assert.assertEquals(result.getBody().getLastName(), prezime);
			Assert.assertEquals(result.getBody().getBirthDate(), datum_rodjenja);
			Assert.assertEquals(result.getBody().getMembershipTypeName(), tip_clastva);
			Assert.assertEquals(result.getBody().getRoleName(), uloga);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//UPDATE BY ID
		String updateNaziv="UnitTestUpdateReportType";
		Member updateClan=new Member(datum,aktivan,updateNaziv,prezime,datum_rodjenja,tip_clastva,uloga);
		request=new HttpEntity<>(updateClan,headers);

		try {
			//System.out.println(uriB);
			restTemplate.put(uriM,request);
			Assert.assertEquals(memberRepository.findById(id).get().getFirstName(),updateNaziv);
		}
		catch (HttpClientErrorException ex){
			Assert.fail();
		}

		//DELETE BY ID
		try {
			restTemplate.delete(uriM);
			List<Member> memberList=memberRepository.findAll();
			Boolean postoji=false;
			for (Member cl :memberList) {
				if (cl.getId()==id) {
					postoji=true;
					break;
				}
			}
			Assert.assertEquals(false,postoji);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//DELETE BY ID ERROR
		try {
			restTemplate.delete(uriM);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

		//GET BY ID ERROR
		try {
			ResponseEntity<Member> result=restTemplate.getForEntity(uriM,Member.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

	}

	@Test
	public void testReservation() throws URISyntaxException {
		RestTemplate restTemplate=new RestTemplate();

		//testiranje reservation
		final String uri1="http://localhost:"+randomServerPort+"/reservation";
		URI uriR=new URI(uri1);
		final String uri2="http://localhost:"+randomServerPort+"/member";
		URI uriM=new URI(uri2);
		final String uri3="http://localhost:"+randomServerPort+"/bookReservation";
		URI uriB=new URI(uri3);

		Date datum_rez=new Date();

		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/json");

		Member member=new Member(new Date(),false,"ime","prezime",new Date(),"korisnik","korisnik");
		HttpEntity<Member> requestM=new HttpEntity<>(member,headers);
		ResponseEntity<Member> resultM=restTemplate.postForEntity(uriM,requestM,Member.class);
		member.setId(resultM.getBody().getId());

		Book book=new Book("proba",new Date(),true,"naziv knjige neki","roman","sarajevo publishing",false);
		HttpEntity<Book> requestB=new HttpEntity<>(book,headers);
		ResponseEntity<Book> resultB=restTemplate.postForEntity(uriB,requestB,Book.class);
		book.setId(resultB.getBody().getId());

		BookMemberReservation rezervation=new BookMemberReservation(member,book,datum_rez);
		HttpEntity<BookMemberReservation> requestR = new HttpEntity<>(rezervation,headers);
		ResponseEntity<BookMemberReservation> resultR=restTemplate.postForEntity(uriR,requestR,BookMemberReservation.class);

		//POST
		Integer id=0;
		try {
			//ResponseEntity<BookMemberReservation> resultR = restTemplate.postForEntity(uriR,requestR, BookMemberReservation.class);
			id=resultR.getBody().getId();
			Assert.assertEquals(201, resultR.getStatusCodeValue());
			Assert.assertEquals(resultR.getBody().getBook(),book);
			Assert.assertEquals(resultR.getBody().getBook(),reservationRepository.findById(id).get().getBook());
			Assert.assertEquals(resultR.getBody().getMember(),member);
			Assert.assertEquals(resultR.getBody().getMember(),reservationRepository.findById(id).get().getMember());
			Assert.assertEquals(resultR.getBody().getDateTimeOfReservation(),datum_rez);
			Assert.assertEquals(resultR.getBody().getDateTimeOfReservation(),reservationRepository.findById(id).get().getDateTimeOfReservation());
		}
		catch (HttpClientErrorException e){
			Assert.fail();
		}

		//POST ERROR
		BookMemberReservation rezervacijaError=new BookMemberReservation(member,book,null);
		requestR= new HttpEntity<>(rezervacijaError,headers);
		try {
			restTemplate.postForEntity(uriR,requestR,BookMemberReservation.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(400,ex.getRawStatusCode());
		}

		//GET BY ID
		String uriRbyID=uri1+"/"+id;
		uriR=new URI(uriRbyID);

		try {
			ResponseEntity<BookMemberReservation> result = restTemplate.getForEntity(uriR, BookMemberReservation.class);
			Assert.assertEquals(200, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getMember(), member);
			Assert.assertEquals(result.getBody().getBook(), book);
			Assert.assertEquals(result.getBody().getDateTimeOfReservation(), datum_rez);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//UPDATE BY ID
		Date updateDatum=new Date();
		BookMemberReservation updateReservation=new BookMemberReservation(member,book,updateDatum);
		requestR=new HttpEntity<>(updateReservation,headers);

		try {
			restTemplate.put(uriR,requestR);
			//Assert.assertEquals(reservationRepository.findById(id).get().getBook(),updateDatum);
		}
		catch (HttpClientErrorException ex){
			Assert.fail();
		}

		//DELETE BY ID
		try {
			restTemplate.delete(uriR);
			List<BookMemberReservation> bookMemberReservationList=reservationRepository.findAll();
			Boolean postoji=false;
			for (BookMemberReservation rez :bookMemberReservationList) {
				if (rez.getId()==id) {
					postoji=true;
					break;
				}
			}
			Assert.assertEquals(false,postoji);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//DELETE BY ID ERROR
		try {
			restTemplate.delete(uriR);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

		//GET BY ID ERROR
		try {
			ResponseEntity<BookMemberReservation> result=restTemplate.getForEntity(uriR,BookMemberReservation.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

	}
}
