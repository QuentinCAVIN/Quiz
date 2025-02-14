package com.ynov.service;

import com.ynov.dto.AnswerDto;
import com.ynov.dto.QuizzDto;

import java.util.List;


public interface IAnswerService {

    void createAnswer(AnswerDto answerDto, long questionId);
    List<AnswerDto> getAnswersByQuestionId(long questionId);
}