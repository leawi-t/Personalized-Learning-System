package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// to do create a filter for difficulty

@Repository
public interface FlashCardRepo extends JpaRepository<FlashCard, Long>{
    @Query("""
            SELECT f FROM FlashCard f where
            LOWER(f.tags) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(f.question) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(f.answer) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    public List<FlashCard> searchFlashCard(String keyword);

    public List<FlashCard> findByTopicId(long topicId);
}
