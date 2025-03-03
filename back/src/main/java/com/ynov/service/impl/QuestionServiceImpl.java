package com.ynov.service.impl;

import com.ynov.component.AnswerMapper;
import com.ynov.component.QuestionMapper;
import com.ynov.dto.QuestionDto;
import com.ynov.dto.QuizzDto;
import com.ynov.model.Answer;
import com.ynov.model.Question;
import com.ynov.model.Quizz;
import com.ynov.repository.QuestionRepository;
import com.ynov.repository.QuizzRepository;
import com.ynov.service.IAnswerService;
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
    private final IAnswerService answerService;
    private final QuestionRepository questionRepository;
    private final QuizzRepository quizzRepository;

    @Override
    public Long createQuestion(QuestionDto questionDto, Long quizzId) {
        Quizz quizzSearched = quizzRepository.findById(quizzId);
        if (quizzSearched != null) {
            Question question = QuestionMapper.mapQuestionDtoToQuestion(questionDto);
            question.setQuizz(quizzSearched);
            questionRepository.persist(question);
            log.info("Created question: {}", question.getTitle());
            return question.id;
        }
        log.info("Could not find quizz with id: {}", quizzId);
        return null;
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

    @Override
    public boolean updateQuestion(Long quizzId, Long questionId, QuestionDto questionDto) {
        Optional<QuizzDto> quizz = quizzService.getQuizzById(quizzId);
        if (quizz.isEmpty()) {
            log.info("Quizz non trouvé :(.");
            return false;
        }

        Question question = questionRepository.findById(questionId);
        if (question == null) {
            log.info("Question non trouvée :(.");
            return false;
        }

        if (!question.getQuizz().id.equals(quizzId)) {
            log.info("La question n'appartient pas à ce quiz.");
            return false;
        }

        question.setTitle(questionDto.getTitle());
        List<Answer> newAnswers = questionDto.getAnswers()
                .stream()
                .map(AnswerMapper::mapAnswerDtoToAnswer)
                .toList();
        question.getAnswers().clear();

        for (Answer newAnswer : newAnswers) {
            newAnswer.setQuestion(question);

            question.getAnswers().add(newAnswer);
            answerService.createAnswer(AnswerMapper.mapAnswerToAnswerDto(newAnswer), questionId);
        }

        questionRepository.persist(question);
        log.info("Modified question: {}", question.getTitle());
        return true;
    }
}
