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
public class UpdateQuestionIT {
    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void updateQuestionShouldUpdateExistingQuestion() {
        TestHelper.createUserInDB();
        TestHelper.createQuestion();

        QuestionDto question = new QuestionDto();
        question.setTitle("Modification de la question");
        RestAssured
                .given()
                .contentType(ContentType.JSON).body(question)

                .when()
                .put("/api/quiz/1/questions/1")

                .then().body(is(emptyOrNullString()))
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void updateQuestionShouldNotUpdateNonExistentQuestion() {
        TestHelper.createUserInDB();

        QuestionDto question = new QuestionDto();
        question.setTitle("Modification de la question");
        RestAssured
                .given()
                .contentType(ContentType.JSON).body(question)

                .when()
                .put("/api/quiz/1/questions/1000")

                .then().body(is(emptyOrNullString()))
                .statusCode(404);
    }
}
