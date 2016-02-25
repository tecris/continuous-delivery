package org.terra.bs.rest;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.terra.bs.entities.Author;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthorEndpointIT {

    private static final String AUTHORS_REST_URL = "http://localhost:8080/bookstore/rest/authors";

    @Test
    public void testGetAuthor() throws JsonProcessingException {

        String expectedEmail = "mihail.sadoveanu@email.org";
        String expectedFirstName = "Mihail";
        String expectedLastName = "Sadoveanu";

        Long authorId = this.createAuthor(expectedEmail, expectedFirstName, expectedLastName);

        get(AUTHORS_REST_URL + "/" + authorId).then().body("email", equalTo(expectedEmail))
                .body("firstName", equalTo(expectedFirstName)).body("firstName", equalTo(expectedFirstName));
    }

    @Test
    public void testUpdateAuthor() throws JsonProcessingException {

        String expectedEmail = "mihail.sadoveanu@email.org";
        String expectedFirstName = "Mihal";
        String expectedLastName = "Sadoeanu";

        String updatedEmail = "Mihail.Sadoveanu@email.org";
        String updatedFirstName = "Mihail";
        String updatedLastName = "Sadoveanu";

        Long authorId = this.createAuthor(expectedEmail, expectedFirstName, expectedLastName);
        Author author = this.buildAuthor(updatedEmail, updatedFirstName, updatedLastName);
        author.setAuthorId(authorId);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(author);
        given().contentType("application/json").body(jsonInString).when().put(AUTHORS_REST_URL + "/" + authorId);

        get(AUTHORS_REST_URL + "/" + authorId).then().body("email", equalTo(updatedEmail))
                .body("firstName", equalTo(updatedFirstName)).body("lastName", equalTo(updatedLastName));
    }

    @Test
    public void testDeleteAuthor() throws JsonProcessingException {

        String expectedEmail = "mihail.sadoveanu@email.org";
        String expectedFirstName = "Mihail";
        String expectedLastName = "Sadoveanu";

        Long authorId = this.createAuthor(expectedEmail, expectedFirstName, expectedLastName);
        given().delete(AUTHORS_REST_URL + "/" + authorId);
        given().expect().statusCode(404).get(AUTHORS_REST_URL + "/" + authorId);
    }

    public Long createAuthor(String email, String firstName, String lastName) throws JsonProcessingException {
        Author author = this.buildAuthor(email, firstName, lastName);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(author);

        author = given().contentType("application/json").body(jsonInString).when().post(AUTHORS_REST_URL)
                .as(Author.class);

        return author.getAuthorId();
    }

    public Long createGenericAuthor() throws JsonProcessingException {

        String email = "mihail.sadoveanu@email.org";
        String firstName = "Mihail";
        String lastName = "Sadoveanu";
        Author author = this.buildAuthor(email, firstName, lastName);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(author);

        author = given().contentType("application/json").body(jsonInString).when().post(AUTHORS_REST_URL)
                .as(Author.class);

        return author.getAuthorId();
    }

    private Author buildAuthor(String email, String firstName, String lastName) {
        Author author = new Author();
        author.setEmail(email);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return author;
    }
}
