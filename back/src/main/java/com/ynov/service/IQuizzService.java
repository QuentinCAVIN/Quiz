package com.ynov.service;

import com.ynov.dto.QuizzDto;

import java.util.List;

public interface IQuizzService {
    public List<QuizzDto> getQuizzesByUser(String userId);
}
