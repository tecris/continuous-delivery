package org.terra.planet.rest;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.terra.planet.model.Planet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlanetEndpointIT {
    
    private static final String HOST = System.getProperty("test.host","localhost");
    private static final String PORT = System.getProperty("test.port","8080");
    private static final String REST_URL = "http://"+HOST+":"+PORT+"/planet/rest/planet";

    @Test
    public void postExample() throws JsonProcessingException {
        
        String expectedName = "Mercury";
        String expectedGalaxy = "Milky Way";
        ObjectMapper mapper = new ObjectMapper();
        Planet planet = new Planet();
        planet.setGalaxy(expectedGalaxy);
        planet.setName(expectedName);
        String jsonInString = mapper.writeValueAsString(planet);

        planet = given().contentType("application/json").body(jsonInString).when().post(REST_URL).as(Planet.class);

        get(REST_URL + "/" + planet.getId()).then().body("name", equalTo(expectedName)).body("galaxy", equalTo(expectedGalaxy));
    }
}
