package br.edu.fatecsjc.lgnspringapi.e2e;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.edu.fatecsjc.lgnspringapi.TestHelper;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class MemberApiTestIT {
    private static String testToken;
    private static Integer memberIdDeleteTest;
    private static Integer memberId;
    private static Long marathonIdTest;
    private static Long groupIdTest;

    @BeforeAll
    public static void CreateTestMembers() {
        RestAssured.baseURI = "http://localhost:8000";
        testToken = TestHelper.getToken();
        marathonIdTest = TestHelper.getMarathon().getId();
        groupIdTest = TestHelper.getGroup().getId();

        String requestBody = "{" 
            + "\"name\":\"Test Member\"," 
            + "\"age\":30," 
            + "\"marathonIds\":[" + marathonIdTest + "]," 
            + "\"groupId\":" + groupIdTest
            + "}";

        memberId = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + testToken)
            .body(requestBody)
            .when()
            .post("/member")
            .then()
            .statusCode(201)
            .extract()
            .path("id");
        
        requestBody = "{" 
            + "\"name\":\"Test Member Delete\"," 
            + "\"age\":30," 
            + "\"marathonIds\":[" + marathonIdTest + "]," 
            + "\"groupId\":" + groupIdTest
            + "}";
            
        memberIdDeleteTest = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + testToken)
            .body(requestBody)
            .when()
            .post("/member")
            .then()
            .statusCode(201)
            .extract()
            .path("id");
    }

    @Test
    void testGetAllMembers() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/member")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("members-list-schema.json"));
    }

    @Test
    void testGetAllMembersException() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken + "invalid")
            .when()
            .get("/member")
            .then()
            .statusCode(403);
    }

    @Test
    void testGetMemberById() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/member/" + memberId)
            .then()
            .statusCode(200)
            .body("id", equalTo(memberId))
            .body(matchesJsonSchemaInClasspath("member-schema.json"));
    }

    @Test
    void testGetMemberByIdException() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/member/" + memberId + "invalid")
            .then()
            .statusCode(400);
    }

    @Test
    void testUpdateMember() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"name\":\"Updated Member\"," 
            + "\"age\":35," 
            + "\"marathonIds\":[" + marathonIdTest + "]," 
            + "\"groupId\":" + groupIdTest
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/member/" + memberId)
            .then()
            .statusCode(201)
            .body("id", equalTo(memberId))
            .body("name", equalTo("Updated Member"))
            .body("age", equalTo(35))
            .body(matchesJsonSchemaInClasspath("member-schema.json"));
    }

    @Test
    void testUpdateMemberException() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"name\":\"Updated Member\"," 
            + "\"age\":35," 
            + "\"marathonIds\":[" + marathonIdTest + "]," 
            + "\"groupId\":" + groupIdTest
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken + "invalid")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/member/" + memberId)
            .then()
            .statusCode(403);
    }

    @Test
    void testRegisterMember() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"name\":\"New Member\"," 
            + "\"age\":40," 
            + "\"marathonIds\":[" + marathonIdTest + "]," 
            + "\"groupId\":" + groupIdTest
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/member")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", equalTo("New Member"))
            .body("age", equalTo(40))
            .body(matchesJsonSchemaInClasspath("member-schema.json"));
    }

    @Test
    void testRegisterMemberException() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"name\":\"New Member\"," 
            + "\"age\":40," 
            + "\"marathonIds\":[" + marathonIdTest + "]," 
            + "\"groupId\":" + groupIdTest
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken + "invalid")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/member")
            .then()
            .statusCode(403);
    }

    @Test
    void testDeleteMember() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/member/" + memberIdDeleteTest)
            .then()
            .statusCode(204);
    }

    @Test
    void testDeleteMemberException() {
        RestAssured.baseURI = "http://localhost:8000";

        given()
            .header ("Authorization", "Bearer " + testToken + "invalid")
            .when()
            .delete("/member/" + memberIdDeleteTest)
            .then()
            .statusCode(403);
    }

    @AfterAll
    public static void DeleteTestMembers() {
        RestAssured.baseURI = "http://localhost:8000";

        List<Integer> ids = given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .get("/member")  
            .then()
            .statusCode(200)
            .extract()
            .path("id");

        Integer lastId = Collections.max(ids);

        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/member/" + memberId)
            .then()
            .statusCode(204);
        
        given()
            .header("Authorization", "Bearer " + testToken)
            .when()
            .delete("/member/" + lastId)
            .then()
            .statusCode(204);
    }
}