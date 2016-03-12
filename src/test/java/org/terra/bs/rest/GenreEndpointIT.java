package org.terra.bs.rest;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.terra.bs.entities.Genre;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenreEndpointIT {

	private static final String HOST = System.getProperty("test.host","localhost");
	private static final String PORT = System.getProperty("test.port","8080");

	private static final String REST_URL = "http://"+HOST+":"+PORT+"/bookstore/rest/genres";

	@Test
	public void testCreateGenre() throws JsonProcessingException {

		String expectedGenre = "fanta";

		String jsonInString = this.buildGenreJson(expectedGenre);

		given().contentType("application/json").body(jsonInString).when().post(REST_URL).then().body("genre", equalTo(expectedGenre));
	}

	@Test
	public void testGetGenre() throws JsonProcessingException {

		String expectedGenre = "fanta";

		int genreId = this.createGenre(expectedGenre).getGenreId();

		get(REST_URL + "/" + genreId).then().body("genre", equalTo(expectedGenre));
	}

	@Test
	public void testCreateGenreNullGenre() throws JsonProcessingException {

		String jsonInString = this.buildGenreJson(null);

		given().contentType("application/json").body(jsonInString).when().post(REST_URL).then().statusCode(400);
	}

	@Test
	public void testCreateGenreInvalidMinSize() throws JsonProcessingException {

		String jsonInString = this.buildGenreJson("scie");

		given().contentType("application/json").body(jsonInString).when().post(REST_URL).then().statusCode(400);
	}

	@Test
	public void testCreateGenreInvalidMaxSize() throws JsonProcessingException {

		String jsonInString = this.buildGenreJson("science-fict");

		given().contentType("application/json").body(jsonInString).when().post(REST_URL).then().statusCode(400);
	}

	@Test
	public void testUpdateGenre() throws JsonProcessingException {

		String expectedGenre = "fantas";

		String updatedGenre = "fantasy";

		int genreId = this.createGenre(expectedGenre).getGenreId();
		Genre genre = this.buildGenre(updatedGenre);
		genre.setGenreId(genreId);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(genre);
		given().contentType("application/json").body(jsonInString).when().put(REST_URL + "/" + genreId);

		get(REST_URL + "/" + genreId).then().body("genre", equalTo(updatedGenre));
	}

	@Test
	public void testDeleteGenre() throws JsonProcessingException {

		String expectedGenre = "fantasy";

		int genreId = this.createGenre(expectedGenre).getGenreId();
		given().delete(REST_URL + "/" + genreId);
		given().expect().statusCode(404).get(REST_URL + "/" + genreId);
	}

	public Genre createGenre(String genreString) throws JsonProcessingException {

		String jsonInString = this.buildGenreJson(genreString);

		return given().contentType("application/json").body(jsonInString).when().post(REST_URL).as(Genre.class);
	}

	private String buildGenreJson(String genreString) throws JsonProcessingException {

		Genre genre = this.buildGenre(genreString);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(genre);
	}

	private Genre buildGenre(String genreString) {
		Genre genre = new Genre();
		genre.setGenre(genreString);
		return genre;
	}
}
