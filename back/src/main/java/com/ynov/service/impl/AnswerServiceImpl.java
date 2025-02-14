package com.ynov.service.impl;

import com.ynov.component.AnswerMapper;
import com.ynov.dto.AnswerDto;
import com.ynov.model.Answer;
import com.ynov.repository.AnswerRepository;
import com.ynov.service.IAnswerService;
import com.ynov.service.IQuestionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@Transactional
public class AnswerServiceImpl implements IAnswerService {
    private final AnswerRepository answerRepository;
    private final IQuestionService questionService;

     public void createAnswer(AnswerDto answerDto, long questionId){
         Answer answer =  AnswerMapper.mapAnswerDtoToAnswer(answerDto);
         answer.setQuestion(questionService.getQuestionById(questionId));
         answerRepository.persist(answer);
    };

    public List<AnswerDto> getAnswersByQuestionId(long questionId) {
        return answerRepository.findByQuestionId(questionId).stream()
                .map(AnswerMapper::mapAnswerToAnswerDto)
                .collect(Collectors.toList());
    };
}
