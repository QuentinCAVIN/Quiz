package com.ynov.integration.user;

import com.ynov.dto.UserDto;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CreateUserIT {
    static UserDto user = UserDto.builder().username("user").email("user@gmail.com").build();
    @Test
    @TestSecurity(user = "UIDpostUser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "postuser@gmail.com")})
    public void postUsersShouldCreateUser() {
        UserDto user = new UserDto();
        user.setUsername("postUser");
        user.setEmail("postuser@gmail.com");

        RestAssured
                .given()
                .contentType(ContentType.JSON).body(user)

                .when()
                .post("/api/users")

                .then()
                .statusCode(201);
    }

    @Test
    public void postUsersShouldNotCreateUserWhenUserIsNotAuthenticated() {
        UserDto user = new UserDto();
        user.setUsername("postUser");
        user.setEmail("postuser@gmail.com");

        RestAssured
                .given().contentType(ContentType.JSON).body(user)

                .when()
                .post("/api/users")

                .then()
                .statusCode(401);
    }
}
