package com.ynov.integration.quiz.quiz;

import com.ynov.dto.QuizzDto;
import com.ynov.helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.matchesPattern;

@QuarkusTest
public class CreateQuizIT {
    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void postQuizShouldReturnsURLWhereFindResource() {
        TestHelper.createUserInDB();
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
}
