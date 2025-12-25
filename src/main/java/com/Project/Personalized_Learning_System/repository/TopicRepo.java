package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

    public List<Topic> findByCategoryId(long categoryId);

    @Query(""" 
            SELECT t FROM Topic t WHERE
            LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    public List<Topic> searchTopic(String keyword);
}
