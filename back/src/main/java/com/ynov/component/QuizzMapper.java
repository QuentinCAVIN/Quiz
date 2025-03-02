package com.ynov.component;

import com.ynov.dto.QuizzDto;
import com.ynov.model.Quizz;

import java.util.stream.Collectors;

public class QuizzMapper {
    public static QuizzDto mapQuizzToQuizzDto(Quizz quizz) {
        return QuizzDto.builder()
                .title(quizz.getTitle())
                .questions(quizz.getQuestions().stream()
                        .map(QuestionMapper::mapQuestionToQuestionDto)
                        .collect(Collectors.toList()))
                .id(quizz.id)
                .build();
    }
    public static Quizz mapQuizzDtoToQuizz(QuizzDto quizzDto) {
        return Quizz.builder()
                .title(quizzDto.getTitle())
                .description(quizzDto.getDescription())
                .build();
    }
}
