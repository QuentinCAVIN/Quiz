package com.ynov.integration;

import com.ynov.dto.UserDto;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.AttributeType;
import io.quarkus.test.security.SecurityAttribute;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import io.quarkus.test.security.TestSecurity;

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class IssuesIT {

    @Test
    @TestSecurity(user = "userJwt", roles = "viewer")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void testPingEndpoint() {

        UserDto user1 = new UserDto();
        user1.setUsername("User1");

        RestAssured
                .given().contentType(ContentType.JSON).body(user1)

                .when()
                .post("/api/users")

                .then()
                .statusCode(201);

        RestAssured
                .given()

                .when()
                .get("/api/ping")

                .then()
                .statusCode(200)

        .body("status", equalTo("OK"))
        .body("details", equalTo("OK"));
    }
}