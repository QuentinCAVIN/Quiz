package com.ynov.service;

import com.ynov.dto.QuestionDto;
import com.ynov.model.Question;

import java.util.List;

public interface IQuestionService {
    Long createQuestion(QuestionDto questionDto, Long quizzId);
    List<QuestionDto> getQuestionsByQuizId(Long quizzId);
    Question getQuestionById(long id);
    boolean updateQuestion(Long quizzId, Long questionId, QuestionDto questionDto);
}
