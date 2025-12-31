package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
    public Page<Note> findByTopicId(long topicId, Pageable pageable);
}
