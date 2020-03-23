package com.example.Application;

import com.example.Application.Genre.Genre;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetGenresListSuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/genres";
		URI uri = new URI(baseUrl);

		ResponseEntity<Genre> result = restTemplate.getForEntity(uri, Genre.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void testGetGenreByIdSuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		Integer testId = 1;
		final String baseUrl = "http://localhost:" + randomServerPort + "/genres/" + testId;
		URI uri = new URI(baseUrl);

		ResponseEntity<Genre> result = restTemplate.getForEntity(uri, Genre.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(true, result.getBody().getId()==testId);

	}

	@Test
	public void testGetGenreByIdError() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		Integer testId = 100;
		final String baseUrl = "http://localhost:" + randomServerPort + "/genres/" + testId;
		URI uri = new URI(baseUrl);

		try {
			ResponseEntity<Genre> result = restTemplate.getForEntity(uri, Genre.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404, ex.getRawStatusCode());
		}
	}

	@Test
	public void testPostGenreSuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:"+randomServerPort+"/genres";
		URI uri = new URI(baseUrl);

		String testName = "TestGenre";
		Genre genre = new Genre();
		genre.setName(testName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpEntity<Genre> request = new HttpEntity<>(genre, headers);

		ResponseEntity<Genre> result = restTemplate.postForEntity(uri, request, Genre.class);

		Assert.assertEquals(201, result.getStatusCodeValue());
	}

	@Test
	public void testPostGenreError() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:"+randomServerPort+"/genres";
		URI uri = new URI(baseUrl);

		String testName = null;
		Genre genre = new Genre();
		genre.setName(testName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpEntity<Genre> request = new HttpEntity<>(genre, headers);

		try {
			ResponseEntity<Genre> result = restTemplate.postForEntity(uri, request, Genre.class);
			Assert.fail();
		}
		catch(HttpClientErrorException ex) {
			Assert.assertEquals(400, ex.getRawStatusCode());
		}
	}

	@Test
	public void testUpdateGenre() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		Integer testId = 1;
		final String baseUrl = "http://localhost:"+randomServerPort+"/genres/" + testId;
		URI uri = new URI(baseUrl);

		String testName = "Crime";
		Genre genre = new Genre();
		genre.setName(testName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpEntity<Genre> request = new HttpEntity<>(genre, headers);

		try {
			restTemplate.put(uri, request);
			Assert.assertTrue(true);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}
	}

	@Test
	public void testDeleteGenreSuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		Integer testId = 11;
		final String baseUrl = "http://localhost:"+randomServerPort+"/genres/" + testId;
		URI uri = new URI(baseUrl);

		try {
			restTemplate.delete(uri);
			Assert.assertTrue(true);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}
	}

}
