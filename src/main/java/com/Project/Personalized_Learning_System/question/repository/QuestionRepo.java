package com.Project.Personalized_Learning_System.question.repository;

import com.Project.Personalized_Learning_System.question.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    @EntityGraph(attributePaths = {"choices"})
    Optional<Question> findById(Long questionId);

    Page<Question> findAll(Pageable pageable);
}
