package com.Project.Personalized_Learning_System.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {

    @EntityGraph(attributePaths = {"questions", "notes", "flashCards"})
    Optional<Topic> findById(Long topicId);

    Page<Topic> findAll(Pageable pageable);
}
