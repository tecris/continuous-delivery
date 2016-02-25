package org.terra.bs.rest;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.terra.bs.entities.Genre;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenreEndpointIT {

    private static final String GENRES_REST_URL = "http://localhost:8080/bookstore/rest/genres";

    @Test
    public void testGetGenre() throws JsonProcessingException {

        String expectedGenre = "fantasy";

        int genreId = this.createGenre(expectedGenre);

        get(GENRES_REST_URL + "/" + genreId).then().body("genre", equalTo(expectedGenre));
    }

    @Test
    public void testUpdateGenre() throws JsonProcessingException {

        String expectedGenre = "fantas";

        String updatedGenre = "fantasy";

        int genreId = this.createGenre(expectedGenre);
        Genre genre = this.buildGenre(updatedGenre);
        genre.setGenreId(genreId);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(genre);
        given().contentType("application/json").body(jsonInString).when().put(GENRES_REST_URL + "/" + genreId);

        get(GENRES_REST_URL + "/" + genreId).then().body("genre", equalTo(updatedGenre));
    }

    @Test
    public void testDeleteGenre() throws JsonProcessingException {

        String expectedGenre = "fantasy";

        int genreId = this.createGenre(expectedGenre);
        given().delete(GENRES_REST_URL + "/" + genreId);
        given().expect().statusCode(404).get(GENRES_REST_URL + "/" + genreId);
    }

    public int createGenre(String genreString) throws JsonProcessingException {
        Genre genre = this.buildGenre(genreString);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(genre);

        genre = given().contentType("application/json").body(jsonInString).when().post(GENRES_REST_URL).as(Genre.class);

        return genre.getGenreId();
    }

    private Genre buildGenre(String genreString) {
        Genre genre = new Genre();
        genre.setGenre(genreString);
        return genre;
    }
}
