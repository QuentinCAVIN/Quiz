package com.ynov.repository;

import com.ynov.model.Question;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class QuestionRepository implements PanacheRepository<Question> {
    public List<Question> findByQuizzId(Long quizzId) {
        return list("quizz.id", quizzId);
    }
}
