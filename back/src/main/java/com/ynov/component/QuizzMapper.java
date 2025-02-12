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
}
