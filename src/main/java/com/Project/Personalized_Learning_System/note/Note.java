package com.Project.Personalized_Learning_System.note;

import com.Project.Personalized_Learning_System.common.BaseEntity;
import com.Project.Personalized_Learning_System.flashCard.FlashCard;
import com.Project.Personalized_Learning_System.question.model.Question;
import com.Project.Personalized_Learning_System.topic.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "note")
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String fileName;
    private String fileType;
    private long fileSize;
    private String filePath;

    @OneToMany(mappedBy = "sourceNote")
    private List<FlashCard> flashCards;

    @OneToMany(mappedBy = "sourceNote")
    private List<Question> questions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topicId", nullable = false)
    private Topic topic;

    public void addFlashCard(FlashCard flashCard){
        flashCards.add(flashCard);
    }

    public void addQuestion(Question question){
        questions.add(question);
    }
}
