package com.ynov.dto;

import java.util.List;

public record QuizzResponses(QuizzDto quizzDto, List<QuizzDto> questions) {
}
