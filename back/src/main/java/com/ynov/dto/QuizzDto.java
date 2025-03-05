package com.ynov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuizzDto {
    long id;
    String title;
    String description;
    List<QuestionDto> questions = new ArrayList<>();
}
