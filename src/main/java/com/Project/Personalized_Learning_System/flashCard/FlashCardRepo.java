package com.Project.Personalized_Learning_System.flashCard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// to do create a filter for difficulty

@Repository
public interface FlashCardRepo extends JpaRepository<FlashCard, Long>, JpaSpecificationExecutor<FlashCard> {
    Page<FlashCard> findAll(Pageable pageable);
}
