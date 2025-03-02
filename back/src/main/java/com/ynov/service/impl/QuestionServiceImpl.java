package com.ynov.service.impl;

import com.ynov.component.AnswerMapper;
import com.ynov.component.QuestionMapper;
import com.ynov.component.QuizzMapper;
import com.ynov.dto.QuestionDto;
import com.ynov.dto.QuizzDto;
import com.ynov.model.Question;
import com.ynov.model.Quizz;
import com.ynov.repository.QuestionRepository;
import com.ynov.service.IQuestionService;
import com.ynov.service.IQuizzService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements IQuestionService {
    private final IQuizzService quizzService;
    private final QuestionRepository questionRepository;

    @Override
    public void createQuestion(QuestionDto questionDto, Long quizzId) {
        Optional<QuizzDto> quizzSearched = quizzService.getQuizzById(quizzId);
        if (quizzSearched.isPresent()) {
            Quizz quizz = QuizzMapper.mapQuizzDtoToQuizz(quizzSearched.get());
            Question question = QuestionMapper.mapQuestionDtoToQuestion(questionDto);
            question.setAnswers(questionDto.getAnswers().stream()
                    .map(AnswerMapper::mapAnswerDtoToAnswer)
                    .collect(Collectors.toList()));
            question.setQuizz(quizz);
            questionRepository.persist(question);
            log.info("Created question: {}", question.getTitle());
        }
    }

    @Override
    public List<QuestionDto> getQuestionsByQuizId(Long quizzId) {
        return questionRepository.findByQuizzId(quizzId)
                .stream()
                .map(QuestionMapper::mapQuestionToQuestionDto)
                .collect(Collectors.toList());
    }

    public Question getQuestionById(long id){
        return questionRepository.findById(id);
    }
}
