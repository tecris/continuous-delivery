package org.terra.bs.rest;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.terra.bs.entities.Publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PublisherEndpointIT {

	private static final String HOST = System.getProperty("test.host","localhost");
	private static final String PORT = System.getProperty("test.port","8080");

	private static final String REST_URL = "http://"+HOST+":"+PORT+"/bookstore/rest/publishers";

	@Test
	public void testCreatePublisher() throws JsonProcessingException {

		String expectedEmail = "cartea.lumina@email.org";
		String expectedName = "Cartea Lumina";
		String expectedPhone = "0064-9-555-5555";

		String jsonInString = this.buildPublisherJson(expectedEmail, expectedName, expectedPhone);

		given().contentType("application/json").body(jsonInString).when().post(REST_URL).then()
				.body("email", equalTo(expectedEmail)).body("name", equalTo(expectedName))
				.body("phone", equalTo(expectedPhone));

	}

	@Test
	public void testGetPublisher() throws JsonProcessingException {

		String expectedEmail = "cartea.mea@email.org";
		String expectedName = "Cartea Lumina";
		String expectedPhone = "0064-9-555-5555";

		int publisherId = this.createPublisher(expectedEmail, expectedName, expectedPhone);

		get(REST_URL + "/" + publisherId).then().body("email", equalTo(expectedEmail))
				.body("name", equalTo(expectedName)).body("phone", equalTo(expectedPhone));
	}

	@Test
	public void testUpdatePublisher() throws JsonProcessingException {

		String expectedEmail = "cartea.lumina@email.org";
		String expectedName = "Cartea Lumina";
		String expectedPhone = "0064-9-555-5555";

		String updatedEmail = "Cartea.Lumina@email.org";
		String updatedName = "Cartea Lumina";
		String updatedPhone = "0064-9-555-6666";

		int publisherId = this.createPublisher(expectedEmail, expectedName, expectedPhone);
		Publisher publisher = this.buildPublisher(updatedEmail, updatedName, updatedPhone);
		publisher.setPublisherId(publisherId);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(publisher);
		given().contentType("application/json").body(jsonInString).when().put(REST_URL + "/" + publisherId);

		get(REST_URL + "/" + publisherId).then().body("email", equalTo(updatedEmail))
				.body("name", equalTo(updatedName)).body("phone", equalTo(updatedPhone));
	}

	@Test
	public void testDeletePublisher() throws JsonProcessingException {

		String expectedEmail = "cartea.lumina@email.org";
		String expectedName = "Cartea Lumina";
		String expectedPhone = "0064-9-555-5555";

		int publisherId = this.createPublisher(expectedEmail, expectedName, expectedPhone);
		given().delete(REST_URL + "/" + publisherId);
		given().expect().statusCode(404).get(REST_URL + "/" + publisherId);
	}

	public int createPublisher() throws JsonProcessingException {

		String expectedEmail = "cartea.lumina@email.org";
		String expectedName = "Cartea Lumina";
		String expectedPhone = "0064-9-555-5555";


		Publisher publisher = this.buildPublisher(expectedEmail, expectedName, expectedPhone);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(publisher);

		publisher = given().contentType("application/json").body(jsonInString).when().post(REST_URL)
				.as(Publisher.class);

		return publisher.getPublisherId();
	}

	public int createPublisher(String email, String name, String phone) throws JsonProcessingException {

		String jsonInString = this.buildPublisherJson(email, name, phone);

		return given().contentType("application/json").body(jsonInString).when().post(REST_URL)
				.as(Publisher.class).getPublisherId();
	}

	public String buildPublisherJson(String email, String name, String phone) throws JsonProcessingException {
		Publisher publisher = this.buildPublisher(email, name, phone);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(publisher);
	}

	private Publisher buildPublisher(String email, String name, String phone) {
		Publisher publisher = new Publisher();
		publisher.setEmail(email);
		publisher.setName(name);
		publisher.setPhone(phone);
		return publisher;
	}
}
