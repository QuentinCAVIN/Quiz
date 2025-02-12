package com.ynov.service.impl;

import com.ynov.component.QuizzMapper;
import com.ynov.dto.QuizzDto;
import com.ynov.repository.QuizzRepository;
import com.ynov.service.IQuizzService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class QuizzServiceImpl implements IQuizzService {
    private final QuizzRepository quizzRepository;

    @Override
    public List<QuizzDto> getQuizzesByUser(String userId) {
        return quizzRepository.findByUserId(userId)
                .stream()
                .map(QuizzMapper::mapQuizzToQuizzDto)
                .collect(Collectors.toList());
    }
}
