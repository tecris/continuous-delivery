package org.terra.bs.rest;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.terra.bs.entities.Publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PublisherEndpointIT {

    private static final String PUBLISHERS_REST_URL = "http://localhost:8080/bookstore/rest/publishers";

    @Test
    public void testGetPublisher() throws JsonProcessingException {

        String expectedEmail = "mihail.sadoveanu@email.org";
        String expectedName = "Mihail";
        String expectedPhone = "0064-9-555-5555";

        int publisherId = this.createPublisher(expectedEmail, expectedName, expectedPhone);

        get(PUBLISHERS_REST_URL + "/" + publisherId).then().body("email", equalTo(expectedEmail))
                .body("name", equalTo(expectedName)).body("phone", equalTo(expectedPhone));
    }

    @Test
    public void testUpdatePublisher() throws JsonProcessingException {

        String expectedEmail = "mihail.sadoveanu@email.org";
        String expectedName = "Mihal";
        String expectedPhone = "0064-9-555-5555";

        String updatedEmail = "Mihail.Sadoveanu@email.org";
        String updatedName = "Mihail";
        String updatedPhone = "0064-9-555-6666";

        int publisherId = this.createPublisher(expectedEmail, expectedName, expectedPhone);
        Publisher publisher = this.buildPublisher(updatedEmail, updatedName, updatedPhone);
        publisher.setPublisherId(publisherId);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(publisher);
        given().contentType("application/json").body(jsonInString).when().put(PUBLISHERS_REST_URL + "/" + publisherId);

        get(PUBLISHERS_REST_URL + "/" + publisherId).then().body("email", equalTo(updatedEmail))
                .body("name", equalTo(updatedName)).body("phone", equalTo(updatedPhone));
    }

    @Test
    public void testDeletePublisher() throws JsonProcessingException {

        String expectedEmail = "mihail.sadoveanu@email.org";
        String expectedName = "Mihail";
        String expectedPhone = "0064-9-555-5555";

        int publisherId = this.createPublisher(expectedEmail, expectedName, expectedPhone);
        given().delete(PUBLISHERS_REST_URL + "/" + publisherId);
        given().expect().statusCode(404).get(PUBLISHERS_REST_URL + "/" + publisherId);
    }

    public int creategenericPublisher() throws JsonProcessingException {

        String email = "mihail.sadoveanu@email.org";
        String name = "Mihail";
        String phone = "0064-9-555-5555";

        Publisher publisher = this.buildPublisher(email, name, phone);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(publisher);

        publisher = given().contentType("application/json").body(jsonInString).when().post(PUBLISHERS_REST_URL)
                .as(Publisher.class);

        return publisher.getPublisherId();
    }

    public int createPublisher(String email, String name, String phone) throws JsonProcessingException {
        Publisher publisher = this.buildPublisher(email, name, phone);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(publisher);

        publisher = given().contentType("application/json").body(jsonInString).when().post(PUBLISHERS_REST_URL)
                .as(Publisher.class);

        return publisher.getPublisherId();
    }

    private Publisher buildPublisher(String email, String name, String phone) {
        Publisher publisher = new Publisher();
        publisher.setEmail(email);
        publisher.setName(name);
        publisher.setPhone(phone);
        return publisher;
    }
}
