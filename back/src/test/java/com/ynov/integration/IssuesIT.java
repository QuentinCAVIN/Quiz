package com.ynov.integration;

import com.ynov.dto.UserDto;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import io.quarkus.test.security.TestSecurity;

import static org.hamcrest.Matchers.*;

@QuarkusTest
public class IssuesIT {


    @Test
    @TestSecurity(user = "user"+ 1, roles = "viewer")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user1@gmail.com")})
    public void getPingReturnPongWhenDatabaseIsRunning() {
        createUserInDB(1);

        RestAssured
                .given()

                .when()
                .get("/api/ping")

                .then()
                .statusCode(200)
                .body("status", equalTo("OK"))
                .body("details", equalTo("OK"));
    }

    @Test
    @TestSecurity(user = "user" + 2)
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user" + 2 + "@gmail.com")})
    public void postUsersShouldCreateUser() {
        UserDto user = new UserDto();
        user.setUsername("user" + 2);

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
        user.setUsername("user" + 3);

        RestAssured
                .given().contentType(ContentType.JSON).body(user)

                .when()
                .post("/api/users")

                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "user" + 4)
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user" + 4 + "@gmail.com")})
    public void getUsersMeShouldReturnAuthenticatedUser() {
        createUserInDB(4);

        RestAssured
                .when()
                .get("/api/users/me")

                .then().body("username", equalTo("user" + 4))
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "user" + 5)
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user" + 5 + "@gmail.com")})
    public void getUsersMeShouldNotReturnUserAbsentInDB() {
        RestAssured
                .when()
                .get("/api/users/me")

                .then().body(is(emptyOrNullString()))
                .statusCode(404);
    }

    private void createUserInDB(int userNumber) {
        UserDto user = new UserDto();
        user.setUsername("user" + userNumber);
        RestAssured.given().contentType(ContentType.JSON).body(user).when().post("/api/users");
    }
}