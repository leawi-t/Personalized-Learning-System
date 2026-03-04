package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {

    Page<Topic> findAllBySubjectId(long categoryId, Pageable pageable);

    Page<Topic> findAll(Pageable pageable);

    Optional<Topic> findBySubjectIdAndId(long subjectId, long topicId);

    @Query(""" 
            SELECT t FROM Topic t WHERE
            LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Topic> searchTopic(String keyword, Pageable pageable);
}
