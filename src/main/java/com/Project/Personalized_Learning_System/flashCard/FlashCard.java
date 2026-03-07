package com.Project.Personalized_Learning_System.flashCard;

import com.Project.Personalized_Learning_System.common.BaseEntity;
import com.Project.Personalized_Learning_System.topic.Topic;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "flashcard")
public class FlashCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String question;

    @Column(nullable = false, length = 1000)
    private String answer;

    @ElementCollection
    @CollectionTable(name = "flashcard_tags", joinColumns = @JoinColumn(name = "flashcard_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    private int difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}