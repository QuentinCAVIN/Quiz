package com.ynov.integration.quiz.question;

import com.ynov.dto.QuestionDto;
import com.ynov.helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CreateQuestionIT {
    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void postQuizIdQuestionShouldReturnURLWhereFindResource() {
        TestHelper.createUserInDB();
        TestHelper.createQuizInDB();

        QuestionDto question = new QuestionDto();
        question.setTitle("Nouvelle question");
        var response = RestAssured
                .given()
                .contentType(ContentType.JSON).body(question)

                .when()
                .post("/api/quiz/1/questions")

                .then().body(is(emptyOrNullString())).header("Location", matchesPattern(
                        "http://localhost:8081/api/quiz/questions/\\d+"))
                .statusCode(201)
                .extract().response();
        System.out.println(response.header("Location"));
    }
}
