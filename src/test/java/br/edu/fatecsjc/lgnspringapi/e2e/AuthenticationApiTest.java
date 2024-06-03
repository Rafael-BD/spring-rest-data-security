package br.edu.fatecsjc.lgnspringapi.e2e;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class AuthenticationApiTest {

    private static String token;

    public static String getToken() {
        if (token == null) {
            RestAssured.baseURI = "http://localhost:8000";

            String authRequestBody = "{"
                + "\"email\":\"admin@mail.com\","
                + "\"password\":\"admin123\""
                + "}";

            token = given()
                .contentType(ContentType.JSON)
                .body(authRequestBody)
                .when()
                .post("/auth/authenticate")
                .then()
                .extract()
                .path("access_token");
        }

        return token;
    }
}