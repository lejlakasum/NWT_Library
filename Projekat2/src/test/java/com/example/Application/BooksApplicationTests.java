package com.example.Application;

import com.example.Application.Author.Author;
import com.example.Application.Author.AuthorRepository;
import com.example.Application.Book.Book;
import com.example.Application.Book.BookRepository;
import com.example.Application.BookType.BookType;
import com.example.Application.BookType.BookTypeRepository;
import com.example.Application.Borrowing.Borrowing;
import com.example.Application.Copy.Copy;
import com.example.Application.Copy.CopyRepository;
import com.example.Application.CopyAuthors.CopyAuthors;
import com.example.Application.Country.Country;
import com.example.Application.Country.CountryRepository;
import com.example.Application.Genre.Genre;
import com.example.Application.Genre.GenreRepository;
import com.example.Application.Impression.ImpressionDTO;
import com.example.Application.Member.Member;
import com.example.Application.Member.MemberRepository;
import com.example.Application.Publisher.Publisher;
import com.example.Application.Publisher.PublisherRepository;
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
import java.util.Date;
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
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	PublisherRepository publisherRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	BookRepository bookRepository;

	@Test
	void contextLoads() {
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

	@Test
	public void testCountry() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/countries";
		URI uri = new URI(baseUrl);

		String testName = "UnitTestCountry";
		Country newCountry = new Country(testName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Country> request = new HttpEntity<>(newCountry, headers);

		//Test post
		ResponseEntity<Country> result = restTemplate.postForEntity(uri, request, Country.class);

		Integer createdId = result.getBody().getId();

		Assert.assertEquals(201, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getName(), testName);
		Assert.assertEquals(result.getBody().getName(), countryRepository.findById(createdId).get().getName());

		//Test post error
		Country errorCountry = new Country();
		errorCountry.setName(null);
		request = new HttpEntity<>(errorCountry, headers);
		try {
			restTemplate.postForEntity(uri, request, Country.class);
			Assert.fail();
		}
		catch(HttpClientErrorException ex) {
			Assert.assertEquals(400, ex.getRawStatusCode());
		}

		String byIdUrl = baseUrl + "/" + createdId;
		uri = new URI(byIdUrl);

		//Test getById
		result = restTemplate.getForEntity(uri, Country.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getName(), testName);

		//Test updateById
		String testUpdateName = "UnitTestUpdateCountry";
		Country updateCountry = new Country(testUpdateName);

		request = new HttpEntity<>(updateCountry, headers);
		try {
			restTemplate.put(uri, request);
			Assert.assertEquals(countryRepository.findById(createdId).get().getName(), testUpdateName);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById
		try {
			restTemplate.delete(uri);
			List<Country> countries = countryRepository.findAll();
			Boolean contains = false;
			for (Country country : countries) {
				if(country.getId() == createdId) {
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
			result = restTemplate.getForEntity(uri, Country.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
	}

	@Test
	public void testPublisher() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/publishers";
		URI uri = new URI(baseUrl);

		String testName = "UnitTestPublisher";
		Publisher newPublisher = new Publisher(testName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Publisher> request = new HttpEntity<>(newPublisher, headers);

		//Test post
		ResponseEntity<Publisher> result = restTemplate.postForEntity(uri, request, Publisher.class);

		Integer createdId = result.getBody().getId();

		Assert.assertEquals(201, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getName(), testName);
		Assert.assertEquals(result.getBody().getName(), publisherRepository.findById(createdId).get().getName());

		//Test post error
		Publisher errorPublisher = new Publisher();
		errorPublisher.setName(null);
		request = new HttpEntity<>(errorPublisher, headers);
		try {
			restTemplate.postForEntity(uri, request, Publisher.class);
			Assert.fail();
		}
		catch(HttpClientErrorException ex) {
			Assert.assertEquals(400, ex.getRawStatusCode());
		}

		String byIdUrl = baseUrl + "/" + createdId;
		uri = new URI(byIdUrl);

		//Test getById
		result = restTemplate.getForEntity(uri, Publisher.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getName(), testName);

		//Test updateById
		String testUpdateName = "UnitTestUpdatePublisher";
		Publisher updatePublisher = new Publisher(testUpdateName);

		request = new HttpEntity<>(updatePublisher, headers);
		try {
			restTemplate.put(uri, request);
			Assert.assertEquals(publisherRepository.findById(createdId).get().getName(), testUpdateName);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById
		try {
			restTemplate.delete(uri);
			List<Publisher> publishers = publisherRepository.findAll();
			Boolean contains = false;
			for (Publisher publisher : publishers) {
				if(publisher.getId() == createdId) {
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
			result = restTemplate.getForEntity(uri, Publisher.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
	}

	@Test
	public void testMember() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/members";
		URI uri = new URI(baseUrl);

		Integer testId = 10000;
		String testFirstName = "UnitTestFirst";
		String testLastName = "UnitTestLast";
		Boolean testActive = true;
		Member newMember = new Member(testId, testFirstName, testLastName, testActive);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Member> request = new HttpEntity<>(newMember, headers);

		//Test post
		ResponseEntity<Member> result = restTemplate.postForEntity(uri, request, Member.class);

		Integer createdId = result.getBody().getId();

		Assert.assertEquals(201, result.getStatusCodeValue());
		Assert.assertEquals(createdId, testId);
		Assert.assertEquals(result.getBody().getFirstName(), testFirstName);
		Assert.assertEquals(result.getBody().getFirstName(), memberRepository.findById(createdId).get().getFirstName());

		//Test post error
		Member errorMember = new Member(testId, null, testLastName, testActive);

		request = new HttpEntity<>(errorMember, headers);
		try {
			restTemplate.postForEntity(uri, request, Member.class);
			Assert.fail();
		}
		catch(HttpClientErrorException ex) {
			Assert.assertEquals(400, ex.getRawStatusCode());
		}
		catch (Exception ex) {

		}

		//Test borrowing
		List<Book> books = bookRepository.findAll();
		Book book = new Book();
		if(books.size()!=0) {
			book = books.get(0);
		}
		Date testDate = new Date();
		Boolean testReturned = false;

		String byIdUrl = baseUrl + "/" + createdId;
		URI borrowingURI = new URI(byIdUrl + "/borrowings/" + book.getId());
		Borrowing borrowing = new Borrowing(book, newMember, testDate, testReturned);
		HttpEntity<Borrowing> requestBorrowing = new HttpEntity<>(borrowing, headers);

		try {
			ResponseEntity<Book> resultImpression = restTemplate.postForEntity(borrowingURI, requestBorrowing, Book.class);
			Assert.assertEquals(200, resultImpression.getStatusCodeValue());
		}
		catch (Exception ex) {
			Assert.fail();
		}

		try {
			restTemplate.put(borrowingURI, request);
		}
		catch (Exception ex) {
			Assert.fail();
		}

		uri = new URI(byIdUrl);

		//Test getById
		result = restTemplate.getForEntity(uri, Member.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getFirstName(), testFirstName);

		//Test updateById
		String testUpdateFirstName = "UnitTestUpdateMember";
		Member updateMember = new Member(testId, testUpdateFirstName, testLastName, testActive);

		request = new HttpEntity<>(updateMember, headers);
		try {
			restTemplate.put(uri, request);
			Assert.assertEquals(memberRepository.findById(createdId).get().getFirstName(), testUpdateFirstName);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById
		try {
			restTemplate.delete(uri);
			List<Member> members = memberRepository.findAll();
			Boolean contains = false;
			for (Member member : members) {
				if(member.getId() == createdId) {
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
			result = restTemplate.getForEntity(uri, Member.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
	}

	@Test
	public void testCopyAuthors() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/authors";
		URI uri = new URI(baseUrl);

		List<Country> countries = countryRepository.findAll();
		Country country = new Country();
		if(countries.size()!=0) {
			country = countries.get(0);
		}

		String testFirstName = "TestFirstName";
		String testLastName = "TestLastName";
		Date testBirthDate = new Date();
		Author testAuthor = new Author(testFirstName, testLastName, testBirthDate, country);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Author> request = new HttpEntity<>(testAuthor, headers);

		//Test post author
		ResponseEntity<Author> result = restTemplate.postForEntity(uri, request, Author.class);

		Integer createdId = result.getBody().getId();

		Assert.assertEquals(201, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getFirstName(), testFirstName);
		Assert.assertEquals(result.getBody().getFirstName(), authorRepository.findById(createdId).get().getFirstName());


		//Test post error
		Author errorAuthor = new Author(testFirstName, testLastName, testBirthDate, country);
		errorAuthor.setFirstName(null);

		request = new HttpEntity<>(errorAuthor, headers);
		try {
			restTemplate.postForEntity(uri, request, Author.class);
			Assert.fail();
		}
		catch(HttpClientErrorException ex) {
			Assert.assertEquals(400, ex.getRawStatusCode());
		}

		//Test getById
		uri = new URI(baseUrl + "/" + createdId);
		result = restTemplate.getForEntity(uri, Author.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getFirstName(), testFirstName);

		//Test updateById
		String testUpdateFirstName = "UnitTestUpdateAuthor";
		Author updateAuthor = new Author(testUpdateFirstName, testLastName, testBirthDate, country);

		request = new HttpEntity<>(updateAuthor, headers);
		try {
			restTemplate.put(uri, request);
			Assert.assertEquals(authorRepository.findById(createdId).get().getFirstName(), testUpdateFirstName);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		// Test get all copies by author
		uri = new URI(baseUrl + "/" + createdId + "/copies");
		ResponseEntity<Copy> copyResult = restTemplate.getForEntity(uri, Copy.class);
		Assert.assertEquals(200, copyResult.getStatusCodeValue());

		//Test post author to copy
		List<Copy> copies = copyRepository.findAll();
		Copy copy = new Copy();
		if(copies.size()!=0) {
			copy = copies.get(0);
		}

		uri = new URI("http://localhost:" + randomServerPort + "/copies/" + copy.getId() + "/authors/" + createdId);

		CopyAuthors newCopyAuthors = new CopyAuthors(copy, testAuthor);
		HttpEntity<CopyAuthors> requestCopyAuthor = new HttpEntity<>(newCopyAuthors, headers);
		ResponseEntity<CopyAuthors> resultCopyAuthor = restTemplate.postForEntity(uri, requestCopyAuthor, CopyAuthors.class);

		Integer copyAuthorCreatedId = resultCopyAuthor.getBody().getId();

		Assert.assertEquals(200, resultCopyAuthor.getStatusCodeValue());

		//Test delete author from copy
		try {
			restTemplate.delete(uri);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test delete author by id
		uri = new URI(baseUrl + "/" + createdId);
		try {
			restTemplate.delete(uri);
			List<Author> authors = authorRepository.findAll();
			Boolean contains = false;
			for (Author author : authors) {
				if(author.getId() == createdId) {
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
			uri = new URI(baseUrl + "/" + createdId);
			restTemplate.delete(uri);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}

		//Test getById error
		try {
			result = restTemplate.getForEntity(uri, Author.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
	}

	@Test
	public void testBook() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/books";
		URI uri = new URI(baseUrl);

		String testISBN = "UnitTestISBN";
		Date testPublishedDate = new Date();
		Boolean testAvailable = true;

		List<Copy> copies = copyRepository.findAll();
		Copy copy = new Copy();
		if(copies.size()!=0) {
			copy = copies.get(0);
		}

		List<BookType> bookTypes = bookTypeRepository.findAll();
		BookType bookType = new BookType();
		if(bookTypes.size()!=0) {
			bookType = bookTypes.get(0);
		}

		List<Genre> genres = genreRepository.findAll();
		Genre genre = new Genre();
		if(genres.size()!=0) {
			genre = genres.get(0);
		}

		List<Publisher> publishers = publisherRepository.findAll();
		Publisher publisher = new Publisher();
		if(publishers.size()!=0) {
			publisher = publishers.get(0);
		}


		Book newBook = new Book(testISBN, copy, bookType, genre, publisher, testPublishedDate, testAvailable);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Book> request = new HttpEntity<>(newBook, headers);

		//Test post
		ResponseEntity<Book> result = restTemplate.postForEntity(uri, request, Book.class);

		Integer createdId = result.getBody().getId();

		Assert.assertEquals(201, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getIsbn(), testISBN);
		Assert.assertEquals(result.getBody().getIsbn(), bookRepository.findById(createdId).get().getIsbn());

		//Test post error
		Book errorBook = new Book();
		errorBook.setIsbn(null);
		request = new HttpEntity<>(errorBook, headers);
		try {
			restTemplate.postForEntity(uri, request, Book.class);
			Assert.fail();
		}
		catch(Exception ex) {

		}

		//Test insert impression
		List<Member> members = memberRepository.findAll();
		Member member = new Member();
		if(members.size()!=0) {
			member = members.get(0);
		}
		String testComment = "TestComment";
		Integer testRating = 4;

		String byIdUrl = baseUrl + "/" + createdId;
		URI impressionURI = new URI(byIdUrl + "/impressions");
		ImpressionDTO impressionDTO = new ImpressionDTO(testComment, testRating, member);
		HttpEntity<ImpressionDTO> requestImpression = new HttpEntity<>(impressionDTO, headers);

		try {
			ResponseEntity<ImpressionDTO[]> resultImpression = restTemplate.postForEntity(impressionURI, requestImpression, ImpressionDTO[].class);
			Assert.assertEquals(200, resultImpression.getStatusCodeValue());
		}
		catch (Exception ex) {
			Assert.fail();
		}

		uri = new URI(byIdUrl);

		//Test getById
		result = restTemplate.getForEntity(uri, Book.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody().getIsbn(), testISBN);

		//Test updateById
		String testUpdateISBN = "UnitTestUpdateBookISBN";
		Book updateBook = new Book(testUpdateISBN, copy, bookType, genre, publisher, testPublishedDate, testAvailable);

		request = new HttpEntity<>(updateBook, headers);
		try {
			restTemplate.put(uri, request);
			Assert.assertEquals(bookRepository.findById(createdId).get().getIsbn(), testUpdateISBN);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//Test deleteById
		try {
			restTemplate.delete(uri);
			List<Book> books = bookRepository.findAll();
			Boolean contains = false;
			for (Book book : books) {
				if(book.getId() == createdId) {
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
			result = restTemplate.getForEntity(uri, Book.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
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

}
