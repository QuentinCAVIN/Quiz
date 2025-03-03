package com.ynov.integration;

import com.ynov.dto.QuestionDto;
import com.ynov.dto.QuizzDto;
import com.ynov.dto.UserDto;
import com.ynov.model.Question;
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
    public void getUsersMeShouldReturnCurrentAuthenticatedUser() {
        createUserInDB();
        RestAssured
                .when()
                .get("/api/users/me")

                .then().body("username", equalTo("user"))
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "UIDuserAbsentInDB")
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
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void postQuizShouldReturnsURLWhereFindResource() {
        createUserInDB();
        QuizzDto quizz = new QuizzDto();
        quizz.setDescription("");
        quizz.setTitle("Nouveau Quiz");

        RestAssured
                .given()
                .contentType(ContentType.JSON).body(quizz)

                .when()
                .post("/api/quiz")

                .then().header("Location", matchesPattern("http://localhost:8081/api/quiz/\\d+"))
                .statusCode(201);
    }

    @Test
    @TestSecurity(user = "UIDuserwith2Quizzes")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "userwith2Quizzes@gmail.com")})
    public void getQuizzShouldReturnAllQuizzFromUsers() {
        createUserInDB("with2Quizzes");
        createQuizInDB();
        createQuizInDB();

        RestAssured
                .when()
                .get("/api/quiz")

                .then().body("data", hasSize(2))
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "UIDuserwithNoAssociatedQuiz")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "userwithNoAssociatedQuiz@gmail.com")})
    public void getQuizShouldReturnsEmptyListWhenUserHasNoAssociatedQuiz() {
        createUserInDB("withNoAssociatedQuiz");
        RestAssured
                .when()
                .get("/api/quiz")

                .then().body("data", hasSize(0))
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getQuizIdShouldReturnQuiz() {
        createUserInDB();
        createQuizInDB();
        RestAssured
                .when()
                .get("/api/quiz/1")

                .then().body("title", equalTo("Nouveau Quiz"))
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getQuizIdShouldNotReturnInexistantQuiz() {
        createUserInDB();
        RestAssured
                .when()
                .get("/api/quiz/10000")

                .then().body(is(emptyOrNullString()))
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getQuizIdQuestionShouldReturnURLWhereFindResource() {
        createUserInDB();
        createQuizInDB();

        QuestionDto question = new QuestionDto();
        question.setTitle("Nouvelle question");
        RestAssured
                .given()
                .contentType(ContentType.JSON).body(question)

                .when()
                .post("/api/quiz/1/questions")

                .then().body(is(emptyOrNullString())).header("Location", matchesPattern(
                        "http://localhost:8081/api/quiz/questions/\\d+"))
                .statusCode(201);
    }

    //Méthodes utilitaires nécessaires aux tests
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void createUserInDB() {
        UserDto user = new UserDto();
        user.setUsername("user");
        user.setEmail("user@gmail.com");
        RestAssured.given().contentType(ContentType.JSON).body(user).when().post("/api/users");
    }

    private static void createUserInDB(String userSpecification) {
        UserDto user = new UserDto();
        user.setUsername("user"+ userSpecification);
        user.setEmail("user"+ userSpecification + "@gmail.com");
        RestAssured.given().contentType(ContentType.JSON).body(user).when().post("/api/users");
    }

    private static void createQuizInDB() {
        createUserInDB();
        QuizzDto quizz = new QuizzDto();
        quizz.setDescription("");
        quizz.setTitle("Nouveau Quiz");
        RestAssured.given().contentType(ContentType.JSON).body(quizz).when().post("/api/quiz");
    }
}