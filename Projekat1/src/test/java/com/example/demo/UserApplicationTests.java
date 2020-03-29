package com.example.demo;

import com.example.demo.Book.Book;
import com.example.demo.Book.BookRepository;
import com.example.demo.Employee.Employee;
import com.example.demo.Employee.EmployeeRepository;
import com.example.demo.Fee.Fee;
import com.example.demo.Fee.FeeRepository;
import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.MembershipType.MembershipType;
import com.example.demo.MembershipType.MembershipTypeRepository;
import com.example.demo.PaidFee.PaidFeeRepository;
import com.example.demo.Profile.Profile;
import com.example.demo.Profile.ProfileRepository;
import com.example.demo.Role.Role;
import com.example.demo.Role.RoleRepository;
import com.example.demo.Settings.Settings;
import com.example.demo.Settings.SettingsRepository;
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
class UserApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	FeeRepository feeRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MembershipTypeRepository membershipTypeRepository;

	@Autowired
	PaidFeeRepository paidFeeRepository;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	SettingsRepository settingsRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testRole () throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/roles";
		URI uri = new URI(baseUrl);

		String testnoIme="UnitTestRole";
		Role role=new Role(testnoIme);

		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type","application/json");
		HttpEntity<Role> request=new HttpEntity<>(role,headers);

		//Test post
		Integer createdId=0;

		try {
			ResponseEntity<Role> result = restTemplate.postForEntity(uri,request, Role.class);
			createdId=result.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getName(),testnoIme);
			Assert.assertEquals(result.getBody().getName(),roleRepository.findById(createdId).get().getName());

		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test post error
		Role errorRole=new Role(null);
		request=new HttpEntity<>(errorRole,headers);
		try{
			restTemplate.postForEntity(uri,request,Role.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(400,e.getRawStatusCode());
		}

		String byIdUrl=baseUrl+"/"+createdId;
		uri=new URI(byIdUrl);

		//Test getById
		try {
			ResponseEntity<Role> result=restTemplate.getForEntity(uri,Role.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getName(),testnoIme);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test updateById
		String testnoUpdateIme="UnitTestUpdateRole";
		Role updateRole=new Role(testnoUpdateIme);

		request=new HttpEntity<>(updateRole,headers);
		try{
			restTemplate.put(uri,request);
			Assert.assertEquals(roleRepository.findById(createdId).get().getName(),testnoUpdateIme);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test delete
		try {
			restTemplate.delete(uri);
			List<Role> roles=roleRepository.findAll();
			Boolean sadrzi=false;
			for (Role uloga :roles){
				if (uloga.getId()==createdId){
					sadrzi=true;
					break;
				}
			}
			Assert.assertEquals(false,sadrzi);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test deleteById error
		try {
			restTemplate.delete(uri);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}

		//Test getById error
		try {
			ResponseEntity<Role> result=restTemplate.getForEntity(uri,Role.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}
	}

	@Test
	public void testBook () throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/books";
		URI uri = new URI(baseUrl);

		String testIsbn="UnitTestBook";
		Book book=new Book(testIsbn);

		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type","application/json");
		HttpEntity<Book> request=new HttpEntity<>(book,headers);

		//Test post
		Integer createdId=0;

		try {
			ResponseEntity<Book> result = restTemplate.postForEntity(uri,request, Book.class);
			createdId=result.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getIsbn(),testIsbn);
			Assert.assertEquals(result.getBody().getIsbn(),bookRepository.findById(createdId).get().getIsbn());

		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test post error
		Book errorBook=new Book(null);
		request=new HttpEntity<>(errorBook,headers);
		try{
			restTemplate.postForEntity(uri,request,Book.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(400,e.getRawStatusCode());
		}

		String byIdUrl=baseUrl+"/"+createdId;
		uri=new URI(byIdUrl);

		//Test getById
		try {
			ResponseEntity<Book> result=restTemplate.getForEntity(uri,Book.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getIsbn(),testIsbn);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test updateById
		String testUpdateIsbn="UnitTestUpdateBook";
		Book updateBook=new Book(testUpdateIsbn);

		request=new HttpEntity<>(updateBook,headers);
		try{
			restTemplate.put(uri,request);
			Assert.assertEquals(bookRepository.findById(createdId).get().getIsbn(),testUpdateIsbn);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test delete
		try {
			restTemplate.delete(uri);
			List<Book> books=bookRepository.findAll();
			Boolean sadrzi=false;
			for (Book knjige :books){
				if (knjige.getId()==createdId){
					sadrzi=true;
					break;
				}
			}
			Assert.assertEquals(false,sadrzi);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test deleteById error
		try {
			restTemplate.delete(uri);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}

		//Test getById error
		try {
			ResponseEntity<Book> result=restTemplate.getForEntity(uri,Book.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}
	}

	@Test
	public void testFee () throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/fees";
		URI uri = new URI(baseUrl);

		String testnoIme="UnitTestFee";
		Double vrijednost=12.5;
		Fee fee=new Fee(testnoIme,vrijednost);

		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type","application/json");
		HttpEntity<Fee> request=new HttpEntity<>(fee,headers);

		//Test post
		Integer createdId=0;

		try {
			ResponseEntity<Fee> result = restTemplate.postForEntity(uri,request, Fee.class);
			createdId=result.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getName(),testnoIme);
			Assert.assertEquals(result.getBody().getName(),feeRepository.findById(createdId).get().getName());
			Assert.assertEquals(result.getBody().getValue(),vrijednost);
			Assert.assertEquals(result.getBody().getValue(),feeRepository.findById(createdId).get().getValue());

		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test post error
		Fee errorFee=new Fee(null,null);
		request=new HttpEntity<>(errorFee,headers);
		try{
			restTemplate.postForEntity(uri,request,Fee.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(400,e.getRawStatusCode());
		}

		String byIdUrl=baseUrl+"/"+createdId;
		uri=new URI(byIdUrl);

		//Test getById
		try {
			ResponseEntity<Fee> result=restTemplate.getForEntity(uri,Fee.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getName(),testnoIme);
			Assert.assertEquals(result.getBody().getValue(),vrijednost);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test updateById
		String testnoUpdateIme="UnitTestUpdateFee";
		Double testUpdateVrijednost=15.23;
		Fee updateFee=new Fee(testnoUpdateIme,testUpdateVrijednost);

		request=new HttpEntity<>(updateFee,headers);
		try{
			restTemplate.put(uri,request);
			Assert.assertEquals(feeRepository.findById(createdId).get().getName(),testnoUpdateIme);
			Assert.assertEquals(feeRepository.findById(createdId).get().getValue(),testUpdateVrijednost);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test delete
		try {
			restTemplate.delete(uri);
			List<Fee> fees=feeRepository.findAll();
			Boolean sadrzi=false;
			for (Fee feee :fees){
				if (feee.getId()==createdId){
					sadrzi=true;
					break;
				}
			}
			Assert.assertEquals(false,sadrzi);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test deleteById error
		try {
			restTemplate.delete(uri);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}

		//Test getById error
		try {
			ResponseEntity<Fee> result=restTemplate.getForEntity(uri,Fee.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}
	}

	@Test
	public void testEmployee () throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String urlEmployee = "http://localhost:" + randomServerPort + "/employees";
		final String urlRole = "http://localhost:" + randomServerPort + "/roles";
		final String urlProfile = "http://localhost:" + randomServerPort + "/profiles";
		URI uriRole = new URI(urlRole);
		URI uriProfile = new URI(urlProfile);
		URI uriEmployee = new URI(urlEmployee);

		Double testnaVrijednost=55.25;
		Date testDate=new Date();

		Role role=new Role("uloga");
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type","application/json");
		HttpEntity<Role> request=new HttpEntity<>(role,headers);
		ResponseEntity<Role> resultRole = restTemplate.postForEntity(uriRole,request, Role.class);
		role.setId(resultRole.getBody().getId());

		Profile profil=new Profile("Dolores","Jureta",testDate, role);
		HttpEntity<Profile> requestProfile=new HttpEntity<>(profil,headers);
		ResponseEntity<Profile> resultProfile = restTemplate.postForEntity(uriProfile,requestProfile, Profile.class);
		profil.setId(resultProfile.getBody().getId());

		Employee employee=new Employee(profil,testnaVrijednost);
		HttpEntity<Employee> requestEmployee=new HttpEntity<>(employee,headers);
		ResponseEntity<Employee> resultEmployee= restTemplate.postForEntity(uriEmployee,requestEmployee, Employee.class);

		//Test post
		Integer createdId=0;

		try {
			ResponseEntity<Employee> result = restTemplate.postForEntity(uriEmployee,requestEmployee, Employee.class);
			createdId=resultEmployee.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getProfile(),profil);
			Assert.assertEquals(result.getBody().getProfile(),employeeRepository.findById(createdId).get().getProfile());
			Assert.assertEquals(result.getBody().getSalary(),testnaVrijednost);
			Assert.assertEquals(result.getBody().getSalary(),employeeRepository.findById(createdId).get().getSalary());

		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test post error
		Employee errorEmployee=new Employee(profil,null);
		requestEmployee=new HttpEntity<>(errorEmployee,headers);
		try{
			restTemplate.postForEntity(uriEmployee,requestEmployee,Employee.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(400,e.getRawStatusCode());
		}

		String byIdUrl=urlEmployee+"/"+createdId;
		uriEmployee=new URI(byIdUrl);

		//Test getById
		try {
			ResponseEntity<Employee> result=restTemplate.getForEntity(uriEmployee,Employee.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getSalary(),testnaVrijednost);
			Assert.assertEquals(result.getBody().getProfile(),profil);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test updateById
		Double testUpdateVrijednost=78.23;
		Employee updateEmployee=new Employee(profil,testUpdateVrijednost);

		requestEmployee=new HttpEntity<>(updateEmployee,headers);
		try{
			restTemplate.put(uriEmployee,requestEmployee);
			Assert.assertEquals(employeeRepository.findById(createdId).get().getSalary(),testUpdateVrijednost);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test delete
		try {
			restTemplate.delete(uriEmployee);
			List<Employee> employees=employeeRepository.findAll();
			Boolean sadrzi=false;
			for (Employee employeee :employees){
				if (employeee.getId()==createdId){
					sadrzi=true;
					break;
				}
			}
			Assert.assertEquals(false,sadrzi);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test deleteById error
		try {
			restTemplate.delete(uriEmployee);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}

		//Test getById error
		try {
			ResponseEntity<Employee> result=restTemplate.getForEntity(uriEmployee,Employee.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}


	}

	@Test
	public void testMembershipType () throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/membershiptypes";
		URI uri = new URI(baseUrl);

		String testnoIme="UnitTestMembershipType";
		MembershipType membershipType=new MembershipType(testnoIme);

		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type","application/json");
		HttpEntity<MembershipType> request=new HttpEntity<>(membershipType,headers);

		//Test post
		Integer createdId=0;

		try {
			ResponseEntity<MembershipType> result = restTemplate.postForEntity(uri,request, MembershipType.class);
			createdId=result.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getName(),testnoIme);
			Assert.assertEquals(result.getBody().getName(),membershipTypeRepository.findById(createdId).get().getName());

		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test post error
		MembershipType errorMembershipType=new MembershipType(null);
		request=new HttpEntity<>(errorMembershipType,headers);
		try{
			restTemplate.postForEntity(uri,request,MembershipType.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(400,e.getRawStatusCode());
		}

		String byIdUrl=baseUrl+"/"+createdId;
		uri=new URI(byIdUrl);

		//Test getById
		try {
			ResponseEntity<MembershipType> result=restTemplate.getForEntity(uri,MembershipType.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getName(),testnoIme);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test updateById
		String testUpdateIme="UnitTestUpdateMembershipType";
		MembershipType updateMembershipType=new MembershipType(testUpdateIme);

		request=new HttpEntity<>(updateMembershipType,headers);
		try{
			restTemplate.put(uri,request);
			Assert.assertEquals(membershipTypeRepository.findById(createdId).get().getName(),testUpdateIme);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test delete
		try {
			restTemplate.delete(uri);
			List<MembershipType> membershipTypes=membershipTypeRepository.findAll();
			Boolean sadrzi=false;
			for (MembershipType membershipTypess : membershipTypes){
				if (membershipTypess.getId()==createdId){
					sadrzi=true;
					break;
				}
			}
			Assert.assertEquals(false,sadrzi);
		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//Test deleteById error
		try {
			restTemplate.delete(uri);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}

		//Test getById error
		try {
			ResponseEntity<MembershipType> result=restTemplate.getForEntity(uri,MembershipType.class);
			Assert.fail();
		}catch (HttpClientErrorException e){
			Assert.assertEquals(404,e.getRawStatusCode());
		}
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
