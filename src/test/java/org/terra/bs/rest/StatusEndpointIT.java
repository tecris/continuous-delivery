package org.terra.bs.rest;

import static com.jayway.restassured.RestAssured.get;

import org.junit.Test;

public class StatusEndpointIT {

	private static final String STATUS_REST_URL = "http://localhost:8080/bookstore/rest/status";

	@Test
	public void testGetStatus() {
		
		get(STATUS_REST_URL).then().statusCode(200);
	}
}
