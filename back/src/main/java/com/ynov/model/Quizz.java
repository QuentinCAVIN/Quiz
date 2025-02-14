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
@Table(name = "quizzes")
@Builder
public class Quizz extends PanacheEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "quizz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
}
