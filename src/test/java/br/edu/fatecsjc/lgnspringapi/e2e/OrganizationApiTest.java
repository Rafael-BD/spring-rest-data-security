package br.edu.fatecsjc.lgnspringapi.e2e;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class OrganizationApiTest {

    private static Integer organizationId, organizationIdDeleteTest;

    // Create two organizations for testing
    @BeforeAll
    public static void createOrganization() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"cep\":\"12345\"," 
            + "\"number\":\"123\"," 
            + "\"street\":\"Test Street\"," 
            + "\"city\":\"Test City\"," 
            + "\"state\":\"Test State\"," 
            + "\"country\":\"Test Country\"," 
            + "\"instituition_name\":\"Test Institution\","
            + "\"name\":\"Test Organization\""
            + "}";

        organizationId = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .body(requestBody)
            .when()
            .post("/organization")
            .then()
            .statusCode(201)
            .body("cep", equalTo("12345"))
            .body("number", equalTo("123"))
            .body("street", equalTo("Test Street"))
            .body("city", equalTo("Test City"))
            .body("state", equalTo("Test State"))
            .body("country", equalTo("Test Country"))
            .body("instituition_name", equalTo("Test Institution"))
            .body("name", equalTo("Test Organization"))
            .extract()
            .path("id");
        
            organizationIdDeleteTest = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .body(requestBody)
            .when()
            .post("/organization")
            .then()
            .statusCode(201)
            .body("cep", equalTo("12345"))
            .body("number", equalTo("123"))
            .body("street", equalTo("Test Street"))
            .body("city", equalTo("Test City"))
            .body("state", equalTo("Test State"))
            .body("country", equalTo("Test Country"))
            .body("instituition_name", equalTo("Test Institution"))
            .body("name", equalTo("Test Organization"))
            .extract()
            .path("id");
    }

    @Test
    public void testCreateOrganization() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"cep\":\"12345\"," 
            + "\"number\":\"123\"," 
            + "\"street\":\"Test Street\"," 
            + "\"city\":\"Test City\"," 
            + "\"state\":\"Test State\"," 
            + "\"country\":\"Test Country\"," 
            + "\"instituition_name\":\"Test Institution\","
            + "\"name\":\"Test Organization\""
            + "}";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .body(requestBody)
            .when()
            .post("/organization")
            .then()
            .statusCode(201)
            .body("cep", equalTo("12345"))
            .body("number", equalTo("123"))
            .body("street", equalTo("Test Street"))
            .body("city", equalTo("Test City"))
            .body("state", equalTo("Test State"))
            .body("country", equalTo("Test Country"))
            .body("instituition_name", equalTo("Test Institution"))
            .body("name", equalTo("Test Organization"));
    }

    @Test
    public void testGetAllOrganizations() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .when()
            .get("/organization")
            .then()
            .statusCode(200);
    }

    @Test
    public void testGetOrganizationById() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .when()
            .get("/organization/" + organizationId)
            .then()
            .statusCode(200);
    }

    @Test
    public void testUpdateOrganization() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"cep\":\"12345\"," 
            + "\"number\":\"123\"," 
            + "\"street\":\"Test Street\"," 
            + "\"city\":\"Test City\"," 
            + "\"state\":\"Test State\"," 
            + "\"country\":\"Test Country\"," 
            + "\"instituition_name\":\"Test Institution Updated\","
            + "\"name\":\"Test Organization\""
            + "}";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .body(requestBody)
            .when()
            .put("/organization/" + organizationId)
            .then()
            .statusCode(201)
            .body("instituition_name", equalTo("Test Institution Updated"));
    }

    @Test
    public void testDeleteOrganization() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .when()
            .delete("/organization/" + organizationIdDeleteTest)
            .then()
            .statusCode(204);
    }

    // Delete the organizations created for testing
    @AfterAll
    public static void DeleteTestOrganizations() {
        RestAssured.baseURI = "http://localhost:8000";

        List<Integer> ids = given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
        .when()
        .get("/organization")
        .then()
        .statusCode(200)
        .extract()
        .path("id");

        Integer lastId = Collections.max(ids);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .when()
            .delete("/organization/" + organizationId)
            .then()
            .statusCode(204);
        
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + AuthenticationApiTest.getToken())
            .when()
            .delete("/organization/" + lastId)
            .then()
            .statusCode(204);
    }
}