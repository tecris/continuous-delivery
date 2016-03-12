package org.terra.bs.rest;

import static com.jayway.restassured.RestAssured.get;

import org.junit.Test;

public class StatusEndpointIT {

	private static final String HOST = System.getProperty("test.host","localhost");
	private static final String PORT = System.getProperty("test.port","8080");

	private static final String REST_URL = "http://"+HOST+":"+PORT+"/bookstore/rest/status";

	@Test
	public void testGetStatus() {
		
		get(REST_URL).then().statusCode(200);
	}
}
