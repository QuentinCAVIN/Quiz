package com.ynov.repository;

import com.ynov.model.Answer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AnswerRepository implements PanacheRepository<Answer> {
    public List<Answer> findByQuestionId(Long questionId) {
        return list("question.id", questionId);
    }
}
