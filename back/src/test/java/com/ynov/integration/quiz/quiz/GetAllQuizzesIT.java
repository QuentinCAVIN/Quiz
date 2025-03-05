package com.ynov.integration.quiz.quiz;

import com.ynov.helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class GetAllQuizzesIT {
    @Test
    @TestSecurity(user = "UIDuserwith2Quizzes")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "userwith2Quizzes@gmail.com")})
    public void getQuizShouldReturnAllQuizzFromUsers() {
        TestHelper.createUserInDB("with2Quizzes");
        TestHelper.createQuizInDB();
        TestHelper.createQuizInDB();

        RestAssured
                .when()
                .get("/api/quiz")

                .then().body("data", hasSize(2))
                .statusCode(200);
    }

    //TODO: Le test me semble a revoir : pas la bonne url (getQuiz)
    @Test
    @TestSecurity(user = "UIDuserwithNoAssociatedQuiz")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "userwithNoAssociatedQuiz@gmail.com")})
    public void getQuizShouldReturnsEmptyListWhenUserHasNoAssociatedQuiz() {
        TestHelper.createUserInDB("withNoAssociatedQuiz");
        RestAssured
                .when()
                .get("/api/quiz")

                .then().body("data", hasSize(0))
                .statusCode(200);
    }
}
