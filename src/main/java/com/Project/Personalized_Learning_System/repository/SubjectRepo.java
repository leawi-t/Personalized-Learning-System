package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {
    List<Subject> findByUserId(long userId);

    @Query("""
    SELECT s FROM Subject s
    WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    public List<Subject> searchSubject(String keyword);

    Optional<Subject> findByUserIdAndId(long userId, long categoryId);
}
