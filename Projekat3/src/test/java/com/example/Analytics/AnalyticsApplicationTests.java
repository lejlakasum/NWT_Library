package com.example.Analytics;

import com.example.Analytics.Employee.Employee;
import com.example.Analytics.Employee.EmployeeRepository;
import com.example.Analytics.Report.Report;
import com.example.Analytics.Report.ReportRepository;
import com.example.Analytics.ReportType.ReportType;
import com.example.Analytics.ReportType.ReportTypeRepository;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnalyticsApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	ReportRepository reportRepository;
	@Autowired
	ReportTypeRepository reportTypeRepository;
	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	void contextLoads() {

	}

	@Test
	public void testReportType() throws URISyntaxException {
		RestTemplate restTemplate=new RestTemplate();

		// testiranje report type-a
		final String uri="http://localhost:"+randomServerPort+"/reportType";
		URI uriRT=new URI(uri);

		String naziv="UnitTestReportType";
		ReportType newReportType=new ReportType(naziv);

		HttpHeaders headers= new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<ReportType> request = new HttpEntity<>(newReportType,headers);

		//POST
		Integer id=0;
		try {
			ResponseEntity<ReportType> result = restTemplate.postForEntity(uriRT,request, ReportType.class);
			id=result.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getName(),naziv);
			Assert.assertEquals(result.getBody().getName(),reportTypeRepository.findById(id).get().getName());

		}catch (HttpClientErrorException e){
			Assert.fail();
		}

		//POST ERROR
		ReportType errorReportType=new ReportType(null);
		request= new HttpEntity<>(errorReportType,headers);
		try {
			restTemplate.postForEntity(uriRT,request,ReportType.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(400,ex.getRawStatusCode());
		}

		//GET BY ID
		String uriRTbyID=uri+"/"+id;
		uriRT=new URI(uriRTbyID);

		try {
			ResponseEntity<ReportType> result=restTemplate.getForEntity(uriRT,ReportType.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getName(),naziv);
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//UPDATE BY ID
		String updateNaziv="UnitTestUpdateReportType";
		ReportType updateReportType=new ReportType(updateNaziv);
		request=new HttpEntity<>(updateReportType,headers);

		try {
			System.out.println(uriRT);
			restTemplate.put(uriRT,request);
			Assert.assertEquals(reportTypeRepository.findById(id).get().getName(),updateNaziv);
		}
		catch (HttpClientErrorException ex){
			Assert.fail();
		}

		//DELETE BY ID
		try {
			restTemplate.delete(uriRT);
			List<ReportType> reportTypeList=reportTypeRepository.findAll();
			Boolean postoji=false;
			for (ReportType report :reportTypeList) {
				if (report.getId()==id) {
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
			restTemplate.delete(uriRT);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

		//GET BY ID ERROR
		try {
			ResponseEntity<ReportType> result=restTemplate.getForEntity(uriRT,ReportType.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

	}

	@Test
	public void testEmployee() throws URISyntaxException {
		RestTemplate restTemplate=new RestTemplate();

		//testiranje employee - polovicnog employee-a
		final String uri="http://localhost:"+randomServerPort+"/employee";
		URI uriE=new URI(uri);

		Employee newEmployee=new Employee();
		HttpHeaders headers= new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Employee> request = new HttpEntity<>(newEmployee,headers);

		//POST
		Integer id=0;
		try {
			ResponseEntity<Employee> result = restTemplate.postForEntity(uriE,request, Employee.class);
			id=result.getBody().getId();
			Assert.assertEquals(201, result.getStatusCodeValue());
		}
		catch (HttpClientErrorException e){
			Assert.fail();
		}

		//POST ERROR
		Employee errorEmployee=new Employee(null);
		request= new HttpEntity<>(errorEmployee,headers);
		try {
			restTemplate.postForEntity(uriE,request,Employee.class);
			//Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(400,ex.getRawStatusCode());
		}

		//GET BY ID
		String uriEbyID=uri+"/"+id;
		uriE=new URI(uriEbyID);

		try {
			ResponseEntity<Employee> result=restTemplate.getForEntity(uriE,Employee.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//GET BY ID ERROR
		String uriEMbyID=uri+"/"+999;
		URI uriEM=new URI(uriEMbyID);

		try {
			ResponseEntity<Employee> result=restTemplate.getForEntity(uriEM,Employee.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

		//DELETE BY ID
		try {
			restTemplate.delete(uriE);
			List<Employee> employeeList=employeeRepository.findAll();
			Boolean postoji=false;
			for (Employee employee :employeeList) {
				if (employee.getId()==id) {
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
			restTemplate.delete(uriEM);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

	}

	@Test
	public void testReport() throws URISyntaxException {
		RestTemplate restTemplate=new RestTemplate();
		System.out.println(randomServerPort);
		//testiranje servisa report
		final String uri1="http://localhost:"+randomServerPort+"/report";
		URI uriR=new URI(uri1);
		final String uri2="http://localhost:"+randomServerPort+"/employee";
		URI uriE=new URI(uri2);
		final String uri3="http://localhost:"+randomServerPort+"/reportType/1";
		URI uriRT=new URI(uri3);
		final String uri31="http://localhost:"+randomServerPort+"/reportType";
		URI uriRT1=new URI(uri31);

		String putanja="UnitTestReport";
		Date datum=new Date();

		HttpHeaders headers= new HttpHeaders();
		headers.add("Content-Type", "application/json");

		ResponseEntity<ReportType> resultReportType=restTemplate.getForEntity(uriRT,ReportType.class);

		ReportType reportType=new ReportType("izvjestaj");
		HttpEntity<ReportType> requestRT = new HttpEntity<>(reportType,headers);
		ResponseEntity<ReportType> resultReportType2=restTemplate.postForEntity(uriRT1,requestRT,ReportType.class);
		reportType.setId(resultReportType2.getBody().getId());

		Employee employee=new Employee();
		HttpEntity<Employee> requestE= new HttpEntity<>(employee,headers);
		ResponseEntity<Employee> resultEmployee=restTemplate.postForEntity(uriE,requestE,Employee.class);
		employee.setId(resultEmployee.getBody().getId());

		Report report=new Report(resultReportType.getBody(),employee,datum,putanja);
		HttpEntity<Report> requestR= new HttpEntity<>(report,headers);
		ResponseEntity<Report> resultReport=restTemplate.postForEntity(uriR,requestR,Report.class);

		//POST
		Integer id=0;

		try {
			//ResponseEntity<Report> resultRep=restTemplate.postForEntity(uriR,requestR,Report.class);
			id=resultReport.getBody().getId();
			Assert.assertEquals(201, resultReport.getStatusCodeValue());
			Assert.assertEquals(resultReport.getBody().getReportType().getId(),resultReportType.getBody().getId());
			Assert.assertEquals(resultReport.getBody().getReportType().getId(),reportRepository.findById(id).get().getReportType().getId());
		}
		catch (HttpClientErrorException e){
			Assert.fail();
		}

		//POST ERROR
		Report errorReport=new Report(reportType,employee,null,null);
		requestR= new HttpEntity<>(errorReport,headers);
		try {
			restTemplate.postForEntity(uriR,requestR,Report.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

		//GET BY ID
		String uriRbyID=uri1+"/"+id;
		uriR=new URI(uriRbyID);

		try {
			ResponseEntity<Report> result=restTemplate.getForEntity(uriR,Report.class);
			Assert.assertEquals(200,result.getStatusCodeValue());
			Assert.assertEquals(result.getBody().getReportType().getId(),resultReportType.getBody().getId());
		}
		catch (HttpClientErrorException ex) {
			Assert.fail();
		}

		//UPDATE BY ID
		String updatePutanja="UnitTestUpdateReportType";
		Report updateReport=new Report(resultReportType.getBody(),employee,datum,updatePutanja);
		requestR=new HttpEntity<>(updateReport,headers);

		try {
			restTemplate.put(uriR,requestR);
			Assert.assertEquals(reportRepository.findById(id).get().getPath(),updatePutanja);
		}
		catch (HttpClientErrorException ex){
			Assert.fail();
		}

		//DELETE BY ID
		try {
			restTemplate.delete(uriR);
			List<Report> reportList=reportRepository.findAll();
			Boolean postoji=false;
			for (Report rep :reportList) {
				if (rep.getId()==id) {
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
			ResponseEntity<Report> result=restTemplate.getForEntity(uriR,Report.class);
			Assert.fail();
		}
		catch (HttpClientErrorException ex) {
			Assert.assertEquals(404,ex.getRawStatusCode());
		}

	}


}
