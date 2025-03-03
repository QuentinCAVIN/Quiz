package com.ynov.component;

import com.ynov.dto.QuestionDto;
import com.ynov.model.Question;

import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionDto mapQuestionToQuestionDto(Question question) {
        return QuestionDto.builder()
                .id(question.id)
                .title(question.getTitle())
                .answers(question.getAnswers().stream().map(AnswerMapper::mapAnswerToAnswerDto).collect(Collectors.toList()))
                .build();
    }
    public static Question mapQuestionDtoToQuestion(QuestionDto questionDto) {
        return Question.builder()
                .title(questionDto.getTitle())
                .answers(questionDto.getAnswers().stream().map(AnswerMapper::mapAnswerDtoToAnswer).collect(Collectors.toList()))
                .build();
    }

}
