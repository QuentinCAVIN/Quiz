package com.ynov.component;

import com.ynov.dto.QuizzDto;
import com.ynov.model.Quizz;

public class QuizzMapper {
    public static QuizzDto mapQuizzToQuizzDto(Quizz quizz) {
        return QuizzDto.builder()
                .title(quizz.getTitle())
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
