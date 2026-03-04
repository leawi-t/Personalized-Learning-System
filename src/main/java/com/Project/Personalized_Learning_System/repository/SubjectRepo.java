package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

    Page<Subject> findAll(Pageable pageable);

    boolean existsByNameAndUserId(String name, Long id);
}
