package com.Project.Personalized_Learning_System.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "choice")
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 1000)
    private String text;

    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;
}
