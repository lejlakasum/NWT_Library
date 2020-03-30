package com.example.Analytics;

import com.example.Analytics.Employee.Employee;
import com.example.Analytics.Report.Report;
import com.example.Analytics.ReportType.ReportType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnalyticsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testReport() throws URISyntaxException {
		//testiranje servisa report
		final String uri1="http://localhost:8080/report";
		String testName="UnitTestReport";
		Report newReport=new Report(); //trebam dodati jos jedan konstruktor

		HttpHeaders headers= new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Report> request = new HttpEntity<>(newReport,headers);

	}

	@Test
	public void testReportType() throws URISyntaxException {
		// testiranje report type-a
		final String uri2="http://localhost:8080/reportType";
		String testName="UnitTestReportType";
		ReportType newReportType=new ReportType(); //trebam dodati jos jedan konstruktor

		HttpHeaders headers= new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<ReportType> request = new HttpEntity<>(newReportType,headers);

	}
	@Test
	public void testEmployee() throws URISyntaxException {
		//testiranje employee - polovicnog employee-a
		final String uri3="http://localhost:8080/employee";
		String testName="UnitTestReport";
		Employee newEmployee=new Employee(); //trebam dodati jos jedan konstruktor
		HttpHeaders headers= new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Employee> request = new HttpEntity<>(newEmployee,headers);

	}
}
