package org.planets.rest;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class PlanetEndpointIT {

	@Test
	public void postExample() {
		String expectedName = "Mercury";
		String expectedGalaxy = "Milky Way";
		String myJson = "{\"name\":\""+expectedName+"\",\"galaxy\":\""+expectedGalaxy+"\"}";
		RestAssured.baseURI = "http://127.0.0.1:8080/planets/rest/planets";

		given().contentType("application/json").body(myJson).when().post("");
		
		get("/1").then().body("name", equalTo(expectedName));
		get("/1").then().body("galaxy", equalTo(expectedGalaxy));
	}
}
