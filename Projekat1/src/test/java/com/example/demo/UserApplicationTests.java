package com.example.demo;

import com.example.demo.Book.Book;
import com.example.demo.Fee.Fee;
import com.example.demo.MembershipType.MembershipType;
import com.example.demo.Role.Role;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetRoleSuccess () throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/roles";
		URI uri = new URI(baseUrl);

		ResponseEntity<Role> result = restTemplate.getForEntity(uri, Role.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void testGetMembershipTypeSuccess () throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/membershiptypes";
		URI uri = new URI(baseUrl);

		ResponseEntity<MembershipType> result = restTemplate.getForEntity(uri, MembershipType.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void testGetBookTypeSuccess () throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/books";
		URI uri = new URI(baseUrl);

		ResponseEntity<Book> result = restTemplate.getForEntity(uri, Book.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
	}
}
