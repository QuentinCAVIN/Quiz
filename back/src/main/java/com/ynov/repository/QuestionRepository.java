package com.ynov.repository;

import com.ynov.model.Question;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public class QuestionRepository implements PanacheRepository<Question> {
    public List<Question> findByQuizzId(Long quizzId) {
        return list("quizz.id", quizzId);
    }
}
