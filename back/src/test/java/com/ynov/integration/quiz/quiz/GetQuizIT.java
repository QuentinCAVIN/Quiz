package com.ynov.integration.quiz.quiz;

import com.ynov.dto.QuestionDto;
import com.ynov.dto.QuizzDto;
import com.ynov.helper.ApiResponse;
import com.ynov.helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
        TestHelper.createUserInDB();
        TestHelper.updateQuestionWithResponse();

        ApiResponse <QuizzDto> response = TestHelper.testGetUrl("/api/quiz/1", QuizzDto.class);

        assertThat(response.status()).isEqualTo(200);

        //Verification Quizz
        QuizzDto quizInBody = response.body().orElseThrow();
        assertThat(quizInBody.getTitle()).isEqualTo("Nouveau Quiz");
        assertThat(quizInBody.getQuestions().size()).isGreaterThan(0);

        //Verification Question
        QuestionDto question = quizInBody.getQuestions().getFirst();
        assertThat(question.getTitle()).isEqualTo("Nouvelle question");
        assertThat(question.getAnswers()).isNotEmpty();

        //Verification Réponses
        assertThat(question.getAnswers().get(0).getTitle()).isEqualTo("réponse 1");
        assertThat(question.getAnswers().get(0).getIsCorrect()).isFalse();
        assertThat(question.getAnswers().get(1).getTitle()).isEqualTo("réponse 2");
        assertThat(question.getAnswers().get(1).getIsCorrect()).isTrue();
    }


    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getQuizIdShouldNotReturnInexistantQuiz() {
        TestHelper.createUserInDB();
        var quizResponse = TestHelper.testGetUrl("/api/quiz/10000", QuizzDto.class);
        assertThat(quizResponse.status()).isEqualTo(404);
    }
}
