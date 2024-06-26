package br.edu.fatecsjc.lgnspringapi.e2e;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.edu.fatecsjc.lgnspringapi.TestHelper;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


@TestMethodOrder(OrderAnnotation.class)
public class MarathonApiTestIT {
    private static String testToken;
    private static Integer marathonId;

    @BeforeAll
    public static void CreateTestMarathons() {
        RestAssured.baseURI = "http://localhost:8000";
        testToken = TestHelper.getToken();

        String requestBody = "{" 
            + "\"weight\":10," 
            + "\"score\":100" 
            + "}";

        marathonId = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + testToken)
            .body(requestBody)
            .when()
            .post("/marathon")
            .then()
            .statusCode(201)
            .extract()
            .path("id");
    }

    @Test
    void testGetAllMarathons() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/marathon")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("marathons-list-schema.json"));
    }

    @Test
    void testGetAllMarathonsException() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken + "invalid")
            .when()
            .get("/marathon")
            .then()
            .statusCode(403);
    }

    @Test
    @Order(1)
    void testGetMarathonById() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/marathon/" + marathonId)
            .then()
            .statusCode(200)
            .body("id", equalTo(marathonId))
            .body(matchesJsonSchemaInClasspath("marathon-schema.json"));
    }

    @Test
    void testGetMarathonByIdException() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/marathon/invalid")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(2)
    void testUpdateMarathon() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"weight\":20," 
            + "\"score\":200" 
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/marathon/" + marathonId)
            .then()
            .statusCode(201)
            .body("id", equalTo(marathonId))
            .body("weight", equalTo(20))
            .body("score", equalTo(200))
            .body(matchesJsonSchemaInClasspath("marathon-schema.json"));
    }

    @Test
    void testUpdateMarathonException() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"weight\":20," 
            + "\"score\":200" 
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken + "invalid")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/marathon/" + marathonId)
            .then()
            .statusCode(403);
    }

    @Test
    void testRegisterMarathon() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"weight\":30," 
            + "\"score\":300" 
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/marathon")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("weight", equalTo(30))
            .body("score", equalTo(300))
            .body(matchesJsonSchemaInClasspath("marathon-schema.json"));
    }

    @Test
    void testRegisterMarathonException() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"weight\":30," 
            + "\"score\":300" 
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken + "invalid")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/marathon")
            .then()
            .statusCode(403);
    }

    @Test
    @Order(3)
    void testDeleteMarathon() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/marathon/" + marathonId)
            .then()
            .statusCode(204);
    }

    @Test
    void testDeleteMarathonException() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken + "invalid")
            .when()
            .delete("/marathon/" + marathonId)
            .then()
            .statusCode(403);
    }

    @AfterAll
    public static void DeleteTestMarathons() {
        RestAssured.baseURI = "http://localhost:8000";

        List<Integer> ids = given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/marathon")  
            .then()
            .statusCode(200)
            .extract()
            .path("id");

            Integer lastId = Collections.max(ids);

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/marathon/" + marathonId)
            .then()
            .statusCode(204);
        
        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/marathon/" + lastId)
            .then()
            .statusCode(204);
    }
}