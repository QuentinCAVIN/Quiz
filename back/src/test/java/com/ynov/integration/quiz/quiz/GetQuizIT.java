package com.ynov.integration.quiz.quiz;

import com.ynov.dto.QuizzDto;
import com.ynov.helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GetQuizIT {
    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getQuizIdShouldReturnQuiz() {
        TestHelper.updateQuestionWithResponse();
        RestAssured
                .when()
                .get("/api/quiz/1")

                .then()
                .body("title", equalTo("Nouveau Quiz"))  // Vérifie le titre du quiz
                .body("questions.size()", greaterThan(0)) // Vérifie qu'il y a au moins une question
                .body("questions[0].title", equalTo("Nouvelle question")) // Vérifie le titre de la première question
                .body("questions[0].answers.size()", greaterThan(0)) // Vérifie qu'il y a au moins une réponse
                .body("questions[0].answers[0].title", equalTo("réponse 1"))
                .body("questions[0].answers[0].isCorrect", equalTo(false))
                .body("questions[0].answers[1].title", equalTo("réponse 2"))// Vérifie le titre de la première réponse
                .body("questions[0].answers[1].isCorrect", equalTo(true)) // Vérifie que la réponse est correcte
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getQuizIdShouldNotReturnInexistantQuiz() {
        TestHelper.createUserInDB();
        var quizResponse = TestHelper.testGetUrl("/api/quiz/10000", QuizzDto.class);
        assertEquals(404, quizResponse.status());
    }
}
