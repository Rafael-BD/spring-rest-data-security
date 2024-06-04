package br.edu.fatecsjc.lgnspringapi.e2e;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GroupsApiTestIT {
    private static String testToken;
    private static Integer groupIdDeleteTest;
    private static Integer groupId;

    @BeforeAll
    public static void CreateTestGroups() {
        RestAssured.baseURI = "http://localhost:8000";
        testToken = AuthenticationApiTestIT.getToken();

        String requestBody = "{" 
            + "\"name\":\"Test Group\"," 
            + "\"members\":[]," 
            + "\"organization\":{}" 
            + "}";
        
        groupId = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + testToken)
            .body(requestBody)
            .when()
            .post("/group")
            .then()
            .statusCode(201)
            .extract()
            .path("id");
        
        groupIdDeleteTest = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + testToken)
            .body(requestBody)
            .when()
            .post("/group")
            .then()
            .statusCode(201)
            .extract()
            .path("id");
    }

    @Test
    public void testGetAllGroups() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/group")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("groups-list-schema.json"));
    }

    @Test
    public void testGetGroupById() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/group/" + groupId)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("group-schema.json"));
    }

    @Test
    public void testUpdateGroup() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"name\":\"Test Group Updated\"," 
            + "\"members\":[]," 
            + "\"organization\":{}" 
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/group/" + groupId)
            .then()
            .statusCode(201)
            .body(matchesJsonSchemaInClasspath("group-schema.json"));
    }

    @Test
    public void testRegisterGroup() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"name\":\"Test Group new\"," 
            + "\"members\":[]," 
            + "\"organization\":{}" 
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/group")
            .then()
            .statusCode(201)
            .body(matchesJsonSchemaInClasspath("group-schema.json"));
    }

    @Test
    public void testDeleteGroup() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/group/" + groupIdDeleteTest)
            .then()
            .statusCode(204);
    }

    @AfterAll
    public static void DeleteTestGroups() {
        RestAssured.baseURI = "http://localhost:8000";

        List<Integer> ids = given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/group")  
            .then()
            .statusCode(200)
            .extract()
            .path("id");

        Integer lastId = Collections.max(ids);

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/group/" + groupId)
            .then()
            .statusCode(204);
        
        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/group/" + lastId)
            .then()
            .statusCode(204);
    }
}