package com.ynov.service.impl;

import com.ynov.component.AnswerMapper;
import com.ynov.component.QuestionMapper;
import com.ynov.dto.QuestionDto;
import com.ynov.dto.QuizzDto;
import com.ynov.model.Answer;
import com.ynov.model.Question;
import com.ynov.model.Quizz;
import com.ynov.repository.AnswerRepository;
import com.ynov.repository.QuestionRepository;
import com.ynov.repository.QuizzRepository;
import com.ynov.service.IQuestionService;
import com.ynov.service.IQuizzService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
    private final QuizzRepository quizzRepository;

    @Override
    public void createQuestion(QuestionDto questionDto, Long quizzId) {
        Quizz quizzSearched = quizzRepository.findById(quizzId);
        if (quizzSearched != null) {
            Question question = QuestionMapper.mapQuestionDtoToQuestion(questionDto);
            List<Answer> answers = new ArrayList<>();

            Answer answer1 = new Answer();
            answer1.setTitle("answer1");
            answer1.setCorrect(true);
            answer1.setQuestion(question);
            answers.add(answer1);

            Answer answer2 = new Answer();
            answer2.setTitle("answer2");
            answer2.setCorrect(false);
            answer2.setQuestion(question);
            answers.add(answer2);

            // Associer les réponses à la question
            question.setAnswers(answers);
            log.info("Answers : {}", question.getAnswers().stream().map(Answer::isCorrect).collect(Collectors.toList()));
//            question.setAnswers(questionDto.getAnswers().stream()
//                    .map(AnswerMapper::mapAnswerDtoToAnswer)
//                    .collect(Collectors.toList()));
            question.setQuizz(quizzSearched);
            questionRepository.persist(question);
            log.info("Created question: {}", question.getTitle());
        } else {
            log.info("Could not find quizz with id: {}", quizzId);
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

    /*@Override
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
        question.getAnswers().clear();
        List<Answer> newAnswers = questionDto.getAnswers()
                .stream()
                .map(AnswerMapper::mapAnswerDtoToAnswer)
                .toList();
        question.setAnswers(newAnswers);

        questionRepository.persist(question);
        return true;
    }
     */
}
