package com.ynov.component;

import com.ynov.dto.AnswerDto;
import com.ynov.model.Answer;

public class AnswerMapper {
    public static AnswerDto mapAnswerToAnswerDto(Answer answer) {
        return AnswerDto.builder()
                .title(answer.getTitle())
                .isCorrect(answer.isCorrect())
                .build();
    }
    public static Answer mapAnswerDtoToAnswer(AnswerDto answerDto) {
        return Answer.builder()
                .title(answerDto.getTitle())
                .isCorrect(answerDto.getIsCorrect())
                .build();
    }
}
