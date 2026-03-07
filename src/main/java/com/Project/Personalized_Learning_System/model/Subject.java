package com.Project.Personalized_Learning_System.model;

import com.Project.Personalized_Learning_System.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder

@Entity
@Table(name = "subject", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();

    public void addTopic(Topic topic) {
        topics.add(topic);
        topic.setSubject(this);
    }

    public void removeTopic(Topic topic) {
        topics.remove(topic);
        topic.setSubject(null);
    }
}
