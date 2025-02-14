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
    static UserDto user = UserDto.builder().username("user").email("user@gmail.com").build();

    @Test
    @TestSecurity(user = "UIDuser", roles = "viewer")// TODO : role inutile pour le moment, a supprimer si nécessaire
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getPingReturnPongWhenDatabaseIsRunning() {
        createUserInDB();
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

    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getUsersMeShouldReturnAuthenticatedUser() {
        createUserInDB();
        RestAssured
                .when()
                .get("/api/users/me")

                .then().body("username", equalTo("user"))
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getUsersMeShouldNotReturnUserAbsentInDB() {
        RestAssured
                .when()
                .get("/api/users/me")

                .then().body(is(emptyOrNullString()))
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "UIDuserAbsentInDB")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "useraidb@gmail.com")})
    public void getQuizMeShouldNotReturnUserAbsentInDB() {
        RestAssured
                .when()
                .get("/api/users/me")

                .then().body(is(emptyOrNullString()))
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "UIDuser")
    public void getQuizMeShouldNotReturnUserAbsentInDB2() {
        RestAssured
                .when()
                .get("/api/users/me")

                .then().body(is(emptyOrNullString()))
                .statusCode(400);
    }

    //Méthode utilitaire nécessaire aux tests
    private static void createUserInDB() {
        UserDto user = new UserDto();
        user.setUsername("user");
        user.setEmail("user@gmail.com");
        RestAssured.given().contentType(ContentType.JSON).body(user).when().post("/api/users");
    }
}