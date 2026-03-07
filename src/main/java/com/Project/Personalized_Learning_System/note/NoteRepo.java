package com.Project.Personalized_Learning_System.note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
    Page<Note> findAll(Pageable pageable);
}
