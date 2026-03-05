package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Question;
import com.Project.Personalized_Learning_System.model.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    Page<Question> findAll(Pageable pageable);
}
