package com.ynov.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "questions")
@Builder
public class Question extends PanacheEntity {
    @Column(nullable = false)
    String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_quizz")
    private Quizz quizz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;
}
