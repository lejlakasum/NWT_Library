package com.example.Application;

import com.example.Application.Author.Author;
import com.example.Application.Book.Book;
import com.example.Application.BookType.BookType;
import com.example.Application.BookType.BookTypeRepository;
import com.example.Application.Copy.Copy;
import com.example.Application.Copy.CopyRepository;
import com.example.Application.Country.Country;
import com.example.Application.Genre.Genre;
import com.example.Application.Genre.GenreRepository;
import com.example.Application.Member.Member;
import com.example.Application.Publisher.Publisher;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	GenreRepository genreRepository;
	@Autowired
	BookTypeRepository bookTypeRepository;
	@Autowired
	CopyRepository copyRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetAllMethodsSuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort;

		//Test genres;
		URI uri = new URI(baseUrl + "/genres");
		ResponseEntity<Genre> resultGenre = restTemplate.getForEntity(uri, Genre.class);
		Assert.assertEquals(200, resultGenre.getStatusCodeValue());

		//Test countries
		uri = new URI(baseUrl + "/countries");
		ResponseEntity<Country> resultCountry = restTemplate.getForEntity(uri, Country.class);
		Assert.assertEquals(200, resultCountry.getStatusCodeValue());

		//Test authors
		uri = new URI(baseUrl + "/authors");
		ResponseEntity<Author> resultAuthor = restTemplate.getForEntity(uri, Author.class);
		Assert.assertEquals(200, resultAuthor.getStatusCodeValue());

		//Test books
		uri = new URI(baseUrl + "/books");
		ResponseEntity<Book> resultBook = restTemplate.getForEntity(uri, Book.class);
		Assert.assertEquals(200, resultBook.getStatusCodeValue());

		//Test bookType
		uri = new URI(baseUrl + "/booktypes");
		ResponseEntity<BookType> resultBookType = restTemplate.getForEntity(uri, BookType.class);
		Assert.assertEquals(200, resultBookType.getStatusCodeValue());

		//Test copies
		uri = new URI(baseUrl + "/copies");
		ResponseEntity<Copy> resultCopy = restTemplate.getForEntity(uri, Copy.class);
		Assert.assertEquals(200, resultCopy.getStatusCodeValue());

		//Test members
		uri = new URI(baseUrl + "/members");
		ResponseEntity<Member> resultMember = restTemplate.getForEntity(uri, Member.class);
		Assert.assertEquals(200, resultMember.getStatusCodeValue());

		//Test publishers
		uri = new URI(baseUrl + "/publishers");
		ResponseEntity<Publisher> resultPublisher = restTemplate.getForEntity(uri, Publisher.class);
		Assert.assertEquals(200, resultPublisher.getStatusCodeValue());

	}

	@Test
	public void testGenre() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/genres";
		URI uri = new URI(baseUrl);

		String testName = "UnitTestGenre";
		Genre newGenre = new Genre(testName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Genre> request = new HttpEntity<>(newGenre, headers);

		//Test post
		ResponseEntity<Genre> result = restTemplate.postForEntity(uri, request, Genre.class);

		Integer createdId = result.getBody().getId();

		Assert.assertEquals(201, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getName(), testName);
		Assert.assertEquals(result.getBody().getName(), genreRepository.findById(createdId).get().getName());

		//Test post error
		Genre errorGenre = new Genre();
		errorGenre.setName(null);
		request = new HttpEntity<>(errorGenre, headers);
		try {
			restTemplate.postForEntity(uri, request, Genre.class);
			Assert.fail();
		}
		catch(HttpClientErrorException ex) {
			Assert.assertEquals(400, ex.getRawStatusCode());
		}

		String byIdUrl = baseUrl + "/" + createdId;
		uri = new URI(byIdUrl);

		//Test getById
		result = restTemplate.getForEntity(uri, Genre.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getName(), testName);

		//Test updateById
		String testUpdateName = "UnitTestUpdateGenre";
		Genre updateGenre = new Genre(testUpdateName);

		request = new HttpEntity<>(updateGenre, headers);
		try {
			restTemplate.put(uri, request);
			Assert.assertEquals(genreRepository.findById(createdId).get().getName(), testUpdateName);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById
		try {
			restTemplate.delete(uri);
			List<Genre> genres = genreRepository.findAll();
			Boolean contains = false;
			for (Genre genre : genres) {
				if(genre.getId() == createdId) {
					contains = true;
					break;
				}
			}
			Assert.assertEquals(false, contains);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById error id does not exist
		try {
			restTemplate.delete(uri);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}

		//Test getById error
		try {
			result = restTemplate.getForEntity(uri, Genre.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
	}

	@Test
	public void testBookType() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/booktypes";
		URI uri = new URI(baseUrl);

		String testName = "UnitTestBookType";
		BookType newBookType = new BookType(testName, 5.0, false);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<BookType> request = new HttpEntity<>(newBookType, headers);

		//Test post
		ResponseEntity<BookType> result = restTemplate.postForEntity(uri, request, BookType.class);

		Integer createdId = result.getBody().getId();

		Assert.assertEquals(201, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getName(), testName);
		Assert.assertEquals(result.getBody().getName(), bookTypeRepository.findById(createdId).get().getName());

		//Test post error
		BookType errorBookType = new BookType();
		errorBookType.setName(null);
		request = new HttpEntity<>(errorBookType, headers);
		try {
			restTemplate.postForEntity(uri, request, BookType.class);
			Assert.fail();
		}
		catch(HttpClientErrorException ex) {
			Assert.assertEquals(400, ex.getRawStatusCode());
		}

		String byIdUrl = baseUrl + "/" + createdId;
		uri = new URI(byIdUrl);

		//Test getById
		result = restTemplate.getForEntity(uri, BookType.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getName(), testName);

		//Test updateById
		String testUpdateName = "UnitTestUpdateBookType";
		BookType updateBookType = new BookType(testUpdateName,5.0, false);

		request = new HttpEntity<>(updateBookType, headers);
		try {
			restTemplate.put(uri, request);
			Assert.assertEquals(bookTypeRepository.findById(createdId).get().getName(), testUpdateName);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById
		try {
			restTemplate.delete(uri);
			List<BookType> bookTypes = bookTypeRepository.findAll();
			Boolean contains = false;
			for (BookType bookType : bookTypes) {
				if(bookType.getId() == createdId) {
					contains = true;
					break;
				}
			}
			Assert.assertEquals(false, contains);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById error id does not exist
		try {
			restTemplate.delete(uri);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}

		//Test getById error
		try {
			result = restTemplate.getForEntity(uri, BookType.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
	}

	@Test
	public void testCopy() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/copies";
		URI uri = new URI(baseUrl);

		String testName = "UnitTestCopy";
		Copy newCopy = new Copy(testName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Copy> request = new HttpEntity<>(newCopy, headers);

		//Test post
		ResponseEntity<Copy> result = restTemplate.postForEntity(uri, request, Copy.class);

		Integer createdId = result.getBody().getId();

		Assert.assertEquals(201, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getBookName(), testName);
		Assert.assertEquals(result.getBody().getBookName(), copyRepository.findById(createdId).get().getBookName());

		//Test post error
		Copy errorCopy = new Copy();
		errorCopy.setBookName(null);
		request = new HttpEntity<>(errorCopy, headers);
		try {
			restTemplate.postForEntity(uri, request, Copy.class);
			Assert.fail();
		}
		catch(HttpClientErrorException ex) {
			Assert.assertEquals(400, ex.getRawStatusCode());
		}

		String byIdUrl = baseUrl + "/" + createdId;
		uri = new URI(byIdUrl);

		//Test getById
		result = restTemplate.getForEntity(uri, Copy.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getBookName(), testName);

		//Test updateById
		String testUpdateName = "UnitTestUpdateCopy";
		Copy updateCopy = new Copy(testUpdateName);

		request = new HttpEntity<>(updateCopy, headers);
		try {
			restTemplate.put(uri, request);
			Assert.assertEquals(copyRepository.findById(createdId).get().getBookName(), testUpdateName);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById
		try {
			restTemplate.delete(uri);
			List<Copy> copies = copyRepository.findAll();
			Boolean contains = false;
			for (Copy copy : copies) {
				if(copy.getId() == createdId) {
					contains = true;
					break;
				}
			}
			Assert.assertEquals(false, contains);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById error id does not exist
		try {
			restTemplate.delete(uri);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}

		//Test getById error
		try {
			result = restTemplate.getForEntity(uri, Copy.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
	}

}
