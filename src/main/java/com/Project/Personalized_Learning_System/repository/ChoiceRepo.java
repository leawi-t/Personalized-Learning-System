package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepo extends JpaRepository<Choice, Long> {

    public List<Choice> findByQuestionId(long questionId);
}
