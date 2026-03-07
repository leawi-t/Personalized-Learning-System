package com.Project.Personalized_Learning_System.note.service;

import com.Project.Personalized_Learning_System.note.Note;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class NoteSpecs {
    public static Specification<Note> hasTopicId(Long topicId){
        return ((root, query, cb) -> {
            if (topicId == null) return null;
            return cb.equal(root.join("topic").get("id"), topicId);
        });
    }

    public static Specification<Note> hasName (String name){
        return ((root, query, cb) -> {
            if (name==null) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        });
    }

    public static Specification<Note> hasDescription(String description){
        return ((root, query, cb) -> {
            if (description==null) return null;
            return cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
        });
    }

    public static Specification<Note> dateBetween(LocalDateTime start, LocalDateTime end){
        return ((root, query, cb) -> {
            if (start == null && end==null)
                return null;
            if (end == null)
                return cb.greaterThanOrEqualTo(root.get("createdAt"), start);
            else if (start == null)
                return cb.lessThanOrEqualTo(root.get("createdAt"), end);
            return cb.between(root.get("createdAt"), start, end);
        });
    }
}
