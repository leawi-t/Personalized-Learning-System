package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Question;
import com.Project.Personalized_Learning_System.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {

    @Query("""
    SELECT q FROM Question q
    WHERE q.difficulty BETWEEN :lDifficulty AND :hDifficulty
""")
    public List<Question> filterByDifficulty(int lDifficulty, int hDifficulty);

    public List<Question> findByQuestionType(QuestionType type);

    public List<Question> findByTopicId(long topicId);
}
