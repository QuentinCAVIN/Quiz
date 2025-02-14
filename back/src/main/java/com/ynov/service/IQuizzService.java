package com.ynov.service;

import com.ynov.dto.QuizzDto;

import java.util.List;
import java.util.Optional;

public interface IQuizzService {
    List<QuizzDto> getQuizzesByUser(String userId);
    Long createQuizz(QuizzDto quizzDto, String UID);
    Optional <QuizzDto> getQuizzById(Long quizzId);
}
