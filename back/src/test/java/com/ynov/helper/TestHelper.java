package com.ynov.helper;

import com.ynov.dto.AnswerDto;
import com.ynov.dto.QuestionDto;
import com.ynov.dto.QuizzDto;
import com.ynov.dto.UserDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.Arrays;
import java.util.Optional;


public class TestHelper {

    public static <T> ApiResponse<T> testGetUrl(String url, Class<T> dtoClass) {
        var response = RestAssured
                .when()
                .get(url)
                .then()
                .extract().response();

        var body = Optional.of(response)
                .filter(r -> r.statusCode() >= 200 && r.statusCode() < 300)
                .map(r -> r.as(dtoClass));
        return new ApiResponse<>(response.statusCode(), body, response.headers());
    }

    //TODO Adapter le filter à post
    public static <T> ApiResponse<T> testPostUrl(String url, Class<T> dtoClass) {
        var response = RestAssured
                .when()
                .post(url)
                .then()
                .extract().response();

        var body = Optional.of(response)
                .filter(r -> r.statusCode() >= 200 && r.statusCode() < 300)
                .map(r -> r.as(dtoClass));
        return new ApiResponse<>(response.statusCode(), body, response.headers());
    }

    //TODO Adapter le filter à put
    public static <T> ApiResponse<T> testPutUrl(String url, Class<T> dtoClass) {
        var response = RestAssured
                .when()
                .put(url)
                .then()
                .extract().response();

        var body = Optional.of(response)
                .filter(r -> r.statusCode() >= 200 && r.statusCode() < 300)
                .map(r -> r.as(dtoClass));
        return new ApiResponse<>(response.statusCode(), body, response.headers());
    }

    ////////////////////////////////////////////////////////
    //Création données de test //
    /// ///////////////////////////////////////////////////

    public static void createUserInDB() {
        UserDto user = new UserDto();
        user.setUsername("user");
        user.setEmail("user@gmail.com");
        RestAssured.given().contentType(ContentType.JSON).body(user).when().post("/api/users");
        //testPostUrl("/api/users", UserDto.class);
    }

    public static void createUserInDB(String userWithSpecification) {
        UserDto user = new UserDto();
        user.setUsername("user" + userWithSpecification);
        user.setEmail("user" + userWithSpecification + "@gmail.com");
        RestAssured.given().contentType(ContentType.JSON).body(user).when().post("/api/users");
    }

    public static void createQuizInDB() {
        createUserInDB();
        QuizzDto quizz = new QuizzDto();
        quizz.setDescription("");
        quizz.setTitle("Nouveau Quiz");
        RestAssured.given().contentType(ContentType.JSON).body(quizz).when().post("/api/quiz");
    }

    public static void createQuestion() {
        createQuizInDB();

        QuestionDto question = new QuestionDto();
        question.setTitle("Nouvelle question");
        RestAssured.given().contentType(ContentType.JSON).body(question).when().post("/api/quiz/1/questions");
    }

    //TODO Vérifier l'Association entre la réponse a la Question un fois le travail de mounir récupéré
    public static void updateQuestionWithResponse() {
        createQuestion();
        QuestionDto question = new QuestionDto();
        question.setTitle("Nouvelle question");

        AnswerDto answer1 = new AnswerDto();
        answer1.setTitle("réponse 1");
        answer1.setIsCorrect(false);

        AnswerDto answer2 = new AnswerDto();
        answer2.setTitle("réponse 2");
        answer2.setIsCorrect(true);

        question.getAnswers().add(answer1);
        question.getAnswers().add(answer2);

        RestAssured.given().contentType(ContentType.JSON).body(question).when().put("/api/quiz/1/questions/1");
    }
}