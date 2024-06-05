package br.edu.fatecsjc.lgnspringapi.e2e;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.edu.fatecsjc.lgnspringapi.TestHelper;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GroupsApiTestIT {
    private static String testToken;
    private static Integer groupIdDeleteTest;
    private static Integer groupId;
    private static MemberDTO memberTest;
    private static OrganizationDTO organizationTest;

    @BeforeAll
    public static void CreateTestGroups() {
        RestAssured.baseURI = "http://localhost:8000";
        testToken = TestHelper.getToken();
        memberTest = TestHelper.getMember();
        organizationTest = TestHelper.getOrganization();

        String requestBody = "{" 
            + "\"name\":\"Test Group\"," 
            + "\"members\":[" + memberTest.toString() + "],"
            + "\"organization\":" + organizationTest.toString()
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
            .body("id", equalTo(groupId))
            .body("name", equalTo("Test Group"))
            .body("members", hasSize(0))
            .body("organization", notNullValue())
            .body(matchesJsonSchemaInClasspath("group-schema.json"));
    }

    @Test
    public void testUpdateGroup() {
        RestAssured.baseURI = "http://localhost:8000";

        String requestBody = "{" 
            + "\"name\":\"Test Group Updated\"," 
            + "\"members\":[" + memberTest.toString() + "],"
            + "\"organization\":" + organizationTest.toString()
            + "}";

        given()
            .header("Authorization", "Bearer " + testToken)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/group/" + groupId)
            .then()
            .statusCode(201)
            .body("id", equalTo(groupId))
            .body("name", equalTo("Test Group Updated"))
            .body("members", hasSize(0))
            .body("organization", notNullValue())
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
            .body("id", notNullValue())
            .body("name", equalTo("Test Group new"))
            .body("members", hasSize(0))
            .body("organization", notNullValue())
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