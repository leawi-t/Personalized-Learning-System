package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

    @EntityGraph(attributePaths = {"topics"})
    Optional<Subject> findById(long subjectId);

    Page<Subject> findAll(Pageable pageable);

    boolean existsByNameAndUserId(String name, Long id);
}
