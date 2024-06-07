package br.edu.fatecsjc.lgnspringapi;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class TestHelper {

    private static String token;
    private static final String BASE_URL = "http://localhost:8000";
    private static final String AUTH_ENDPOINT = "/auth/authenticate";
    private static final String ORGANIZATION_ENDPOINT = "/organization";
    private static final String MARATHON_ENDPOINT = "/marathon";
    private static final String MEMBER_ENDPOINT = "/member";
    private static final String GROUP_ENDPOINT = "/group";
    private static final String ADMIN_EMAIL = "admin@mail.com";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static MarathonDTO marathon;
    private static OrganizationDTO organization;
    private static GroupDTO group;

    public static String getToken() {
        if (token == null) {
            RestAssured.baseURI = BASE_URL;

            String authRequestBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", ADMIN_EMAIL, ADMIN_PASSWORD);

            try {
                token = given()
                    .contentType(ContentType.JSON)
                    .body(authRequestBody)
                    .when()
                    .post(AUTH_ENDPOINT)
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("access_token");
            } catch (Exception e) {
                throw new RuntimeException("Failed to get token", e);
            }
        }

        return token;
    }

    public static OrganizationDTO getOrganization() {
        if (organization != null) {
            return organization;
        }

        RestAssured.baseURI = BASE_URL;

        String tokenTest;
        if (token == null) {
            tokenTest = getToken();
        } else {
            tokenTest = token;
        }

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

        try {
            organization =  given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .body(requestBody)
                .when()
                .post(ORGANIZATION_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .as(OrganizationDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get organization", e);
        }
        
        return organization;
    }

    public static MarathonDTO getMarathon() {
        if (marathon != null) {
            return marathon;
        }

        RestAssured.baseURI = BASE_URL;

        String tokenTest;
        if (token == null) {
            tokenTest = getToken();
        } else {
            tokenTest = token;
        }

        String requestBody = "{" 
            + "\"weight\":70," 
            + "\"score\":90"
            + "}";

        try {
            marathon = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .body(requestBody)
                .when()
                .post(MARATHON_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .as(MarathonDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get marathon", e);
        }

        return marathon;
    }

    public static GroupDTO getGroup(){
        if (group != null) {
            return group;
        }

        RestAssured.baseURI = BASE_URL;

        String tokenTest;
        if (token == null) {
            tokenTest = getToken();
        } else {
            tokenTest = token;
        }

        String requestBody = "{\"name\":\"Test Group\"}";

        try {
            group = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .body(requestBody)
                .when()
                .post(GROUP_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .as(GroupDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get group", e);
        }

        return group;
    }

    public static MemberDTO getMember() {
        RestAssured.baseURI = BASE_URL;
        MemberDTO member;
        MarathonDTO marathonTest;
        GroupDTO groupTest;
        String tokenTest;
        String randomName = "Test Member " + RandomStringUtils.randomAlphabetic(3);

        if (marathon == null) {
            marathonTest = getMarathon();
        } else {
            marathonTest = marathon;
        }

        if (group == null) {
            groupTest = getGroup();
        } else {
            groupTest = group;
        }

        if (token == null) {
            tokenTest = getToken();
        } else {
            tokenTest = token;
        }
        
        String requestBody = "{" 
            + "\"name\":\"" + randomName + "\","
            + "\"age\":30,"
            + "\"marathonIds\":[" + marathonTest.getId() + "],"
            + "\"groupId\":" + groupTest.getId()
            + "}";

        try {
            member =  given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .body(requestBody)
                .when()
                .post(MEMBER_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .as(MemberDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get member", e);
        }
        
        return member;
    }
}