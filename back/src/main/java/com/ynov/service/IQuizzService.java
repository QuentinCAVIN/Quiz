package com.ynov.service;

import com.ynov.dto.QuizzDto;

import java.util.List;

public interface IQuizzService {
    List<QuizzDto> getQuizzesByUser(String userId);
    void createQuizz(QuizzDto quizzDto, String UID);
}
