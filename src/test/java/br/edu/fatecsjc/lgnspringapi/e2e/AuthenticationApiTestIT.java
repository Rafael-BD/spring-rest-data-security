package br.edu.fatecsjc.lgnspringapi.e2e;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@TestMethodOrder(OrderAnnotation.class)
public class AuthenticationApiTestIT {

    private static String testToken;
    private static String testId;

    @BeforeAll
    public static void createTestUser() {
        RestAssured.baseURI = "http://localhost:8000";
        testId = RandomStringUtils.randomAlphabetic(10);

        String requestBody = "{" +
            "\"firstname\":\"Test\"," +
            "\"lastname\":\"User" + testId + "\"," +
            "\"email\":\"test" + testId + "@mail.com\"," +
            "\"password\":\"test" + testId + "\"," +
            "\"role\":\"USER\"" +
            "}";
        
        testToken = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/auth/register")
            .then()
            .statusCode(201)
            .extract()
            .path("access_token"); 
    }

    @Test
    @Order(0)
    void testRegister() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{"
            + "\"firstname\":\"Test new\","
            + "\"lastname\":\"User\","
            + "\"email\":\"test@mail.com\","
            + "\"password\":\"test123\","
            + "\"role\":\"USER\""
            + "}";

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/auth/register")
            .then()
            .statusCode(201)
            .body(matchesJsonSchemaInClasspath("authenticate-schema.json"));
    }

    @Test
    @Order(1)
    void testAuthenticate() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }

        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{"
            + "\"email\":\"test"+ testId +"@mail.com\","
            + "\"password\":\"test"+ testId +"\""
            + "}";

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/auth/authenticate")
            .then()         
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("authenticate-schema.json"));
    }

    @Test
    @Order(2)
    void testRefreshToken() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }

        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .post("/auth/refresh-token")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("refresh-token-schema.json"));
    }
}