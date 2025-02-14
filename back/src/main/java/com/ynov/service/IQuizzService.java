package com.ynov.service;

import com.ynov.dto.QuizzDto;

import java.util.List;

public interface IQuizzService {
    List<QuizzDto> getQuizzesByUser(String userId);
    Long createQuizz(QuizzDto quizzDto, String UID);
    QuizzDto getQuizzById(Long quizzId);
}
