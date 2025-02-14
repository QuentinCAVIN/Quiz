package com.ynov.component;

import com.ynov.dto.QuestionDto;
import com.ynov.model.Question;

public class QuestionMapper {

    public static QuestionDto mapQuestionToQuestionDto(Question question) {
        return QuestionDto.builder()
                .title(question.getTitle())
                .build();
    }
    public static Question mapQuestionDtoToQuestion(QuestionDto questionDto) {
        return Question.builder()
                .title(questionDto.getTitle())
                .build();
    }

}
