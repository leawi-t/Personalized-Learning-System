package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.model.Subject;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class SubjectSpecs {
    public static Specification<Subject> hasUserId(Long userId){
        return ((root, query, cb) -> {
            if (userId == null) return null;
            return cb.equal(root.join("user").get("id"), userId);
        });
    }

    public static Specification<Subject> hasName (String name){
        return ((root, query, cb) -> {
            if (name==null) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        });
    }

    public static Specification<Subject> hasDescription(String description){
        return ((root, query, cb) -> {
            if (description==null) return null;
            return cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
        });
    }

    public static Specification<Subject> dateBetween(LocalDateTime start, LocalDateTime end){
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
