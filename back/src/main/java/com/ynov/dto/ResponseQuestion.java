package com.ynov.dto;

import com.ynov.model.Question;

import java.util.List;

public record ResponseQuestion(String title, List<AnswerDto> answerDtos) {
}
