package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.FlashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// to do create a filter for difficulty

@Repository
public interface FlashCardRepo extends JpaRepository<FlashCard, Long>, JpaSpecificationExecutor<FlashCard> {
    Page<FlashCard> findAll(Pageable pageable);
}
