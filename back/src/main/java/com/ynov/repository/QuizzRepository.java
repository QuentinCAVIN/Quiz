package com.ynov.repository;

import com.ynov.model.Quizz;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class QuizzRepository implements PanacheRepository<Quizz> {
    public List<Quizz> findByUserId(String UID) {
        return list("user.uid", UID);
    }
}
