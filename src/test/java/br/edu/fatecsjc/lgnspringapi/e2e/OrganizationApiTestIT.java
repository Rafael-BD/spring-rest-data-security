package br.edu.fatecsjc.lgnspringapi.e2e;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.fatecsjc.lgnspringapi.TestHelper;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OrganizationApiTestIT {

    private static Integer organizationId, organizationIdDeleteTest;
    private static String token;
    private static GroupDTO groupTest;
    private static String groupJson = "";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void CreateOrganization() {
        RestAssured.baseURI = "http://localhost:8000";
        token = TestHelper.getToken();
        groupTest = TestHelper.getGroup();
        
        try {
            groupJson = objectMapper.writeValueAsString(groupTest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to create group test", e);
        }

        String requestBody = "{" 
            + "\"cep\":\"12345\"," 
            + "\"number\":\"123\"," 
            + "\"street\":\"Test Street\"," 
            + "\"city\":\"Test City\"," 
            + "\"state\":\"Test State\"," 
            + "\"country\":\"Test Country\"," 
            + "\"instituition_name\":\"Test Institution\","
            + "\"name\":\"Test Organization\","
            + "\"groups\":[" + groupJson + "]"
            + "}";

        organizationId = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .when()
            .post("/organization")
            .then()
            .statusCode(201)
            .extract()
            .path("id");
        
            organizationIdDeleteTest = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .when()
            .post("/organization")
            .then()
            .statusCode(201)
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
            + "\"name\":\"Test Organization\","
            + "\"groups\":[" + groupJson + "]"
            + "}";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
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
            .body(matchesJsonSchemaInClasspath("organization-schema.json"));
    }

    @Test
    public void testGetAllOrganizations() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/organization")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("organizations-list-schema.json"));
    }

    @Test
    public void testGetOrganizationById() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/organization/" + organizationId)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("organization-schema.json"));
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
            + "\"name\":\"Test Organization\","
            + "\"groups\":[" + groupJson + "]"
            + "}";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .when()
            .put("/organization/" + organizationId)
            .then()
            .statusCode(201)
            .body("instituition_name", equalTo("Test Institution Updated"))
            .body(matchesJsonSchemaInClasspath("organization-schema.json"));
    }

    @Test
    public void testDeleteOrganization() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .when()
            .delete("/organization/" + organizationIdDeleteTest)
            .then()
            .statusCode(204);
    }

    @AfterAll
    public static void DeleteTestOrganizations() {
        RestAssured.baseURI = "http://localhost:8000";

        List<Integer> ids = given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/organization")
        .then()
        .statusCode(200)
        .extract()
        .path("id");

        Integer lastId = Collections.max(ids);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .when()
            .delete("/organization/" + organizationId)
            .then()
            .statusCode(204);
        
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .when()
            .delete("/organization/" + lastId)
            .then()
            .statusCode(204);
    }
}