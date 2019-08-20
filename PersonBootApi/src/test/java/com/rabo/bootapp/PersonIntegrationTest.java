package com.rabo.bootapp;

import java.io.IOException;
import java.sql.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.model.Person;
import com.rabo.model.PersonId;
import com.rabo.service.IPersonService;

import junit.framework.Assert;

@SpringBootTest(value = "RaboBootApp")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class PersonIntegrationTest {

	private static final String REST_SERVICE_URI = "http://localhost:8085/person";
	private static String addPersonUrl;
	private static String updatePersonUrl;
	private static String getPersonUrl;
	private static RestTemplate restTemplate;
	private static HttpHeaders headers;

	private final ObjectMapper objectMapper = new ObjectMapper();
	private static JSONObject personJsonObject;
	private static JSONObject personIdJsonObject;

	private static Person testPerson;
	private static PersonId testPersonId;

	@Autowired
	IPersonService iPersonService;

	@BeforeAll
	void getTestPerson() {
		testPerson = new Person();
		testPerson.setfName("Test");
		testPerson.setlName("case");
		testPerson.setAge(30);
		testPerson.setAddress("Rotterdam");
		testPerson.setDateOfBirth(new Date(19860101));

		testPersonId = new PersonId("Test", "case");
	}

	@BeforeAll
	void prepareRestTemplate() throws JSONException {
		restTemplate = new RestTemplate();

		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		personJsonObject = new JSONObject();
		personJsonObject.put("fName", "Test");
		personJsonObject.put("lName", "Rest");
		personJsonObject.put("age", 22);
		personJsonObject.put("address", "Rotterdam");

		personIdJsonObject = new JSONObject();
		personIdJsonObject.put("fName", "Test");
		personIdJsonObject.put("lName", "Rest");

		addPersonUrl = REST_SERVICE_URI + "/addPerson";
		getPersonUrl = REST_SERVICE_URI + "/getPerson";
		updatePersonUrl = REST_SERVICE_URI + "/updatePerson";
	}

	@Test
	@Order(1)
	public void testAddPerson_1() throws IOException, JSONException {
		prepareRestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
		String personResultAsJsonStr = restTemplate.postForObject(addPersonUrl, request, String.class);
		Assert.assertEquals("Created succesfully", personResultAsJsonStr);
	}

	@Test
	@Order(2)
	public void testGetPerson_1() throws IOException, JSONException {
		HttpEntity<String> request = new HttpEntity<String>(personIdJsonObject.toString(), headers);
		String personResultAsJsonStr = restTemplate.postForObject(getPersonUrl, request, String.class);
		JsonNode root = objectMapper.readTree(personResultAsJsonStr);
		Person result = objectMapper.convertValue(root, Person.class);
		Assert.assertEquals(personJsonObject.getString("address"), result.getAddress());
	}

	@Test
	@Order(3)
	public void testUpdatePerson_1() throws IOException, JSONException {
		personJsonObject.put("address", "Amsterdam");
		prepareHeaders();
		HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
		String personResultAsJsonStr = restTemplate.postForObject(updatePersonUrl, request, String.class);
		JsonNode root = objectMapper.readTree(personResultAsJsonStr);
		Person out = objectMapper.convertValue(root, Person.class);
		Assert.assertEquals("Amsterdam", out.getAddress());

	}

	void prepareHeaders() {
		String plainCreds = "admin:admin";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		headers.add("Authorization", "Basic " + base64Creds);
	}

	@Test
	@Order(4)
	public void testAddPerson() {
		String status = iPersonService.addPerson(testPerson);
		Assert.assertEquals(status, "OK");
	}

	@Test
	@Order(5)
	public void testGetPerson() {
		Person personReturned = iPersonService.findPerson(testPersonId);
		Assert.assertEquals(testPerson.getAge(), personReturned.getAge());
	}

	@Test
	@Order(6)
	public void testUpdatePerson() {
		Person personfetched = iPersonService.findPerson(testPersonId);
		personfetched.setAge(35);
		String status = iPersonService.updatePerson(personfetched);
		Assert.assertEquals(status, "OK");
		personfetched = iPersonService.findPerson(testPersonId);
		Assert.assertEquals(personfetched.getAge(), 35);

	}

	@AfterAll
	void cleanTestData() throws JSONException {
		iPersonService.deletePerson(testPersonId);
		iPersonService.deletePerson(
				new PersonId(personIdJsonObject.getString("fName"), personIdJsonObject.getString("lName")));
	}
}
