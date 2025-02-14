package com.ynov.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "quizzes")
@Builder
public class Quizz extends PanacheEntity {
    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UID", nullable = false)
    private User user;
}
