package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
    public List<Note> findByTopicId(long topicId);
}
