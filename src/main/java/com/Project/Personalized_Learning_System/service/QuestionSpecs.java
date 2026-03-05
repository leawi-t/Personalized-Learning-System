package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.model.Question;
import com.Project.Personalized_Learning_System.model.QuestionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class QuestionSpecs {
    public static Specification<Question> hasTopicId(Long topicId){
        return ((root, query, cb) -> {
            if (topicId == null) return null;
            return cb.equal(root.get("topic").get("id"), topicId);
        });
    }

    public static Specification<Question> hasType(QuestionType questionType){
        return ((root, query, cb) -> {
            if (questionType == null) return null;
            return cb.equal(root.get("questionType"), questionType);
        });
    }

    public static Specification<Question> hasQuestionText (String questionText){
        return ((root, query, cb) -> {
            if (questionText==null) return null;
            return cb.like(cb.lower(root.get("questionText")), "%" + questionText.toLowerCase() + "%");
        });
    }

    public static Specification<Question> hasDifficulty(Integer min, Integer max){
        return ((root, query, cb) -> {
            if (min == null && max == null) return null;
            if (max == null) return cb.greaterThanOrEqualTo(root.get("difficulty"), min);
            if (min == null) return cb.lessThanOrEqualTo(root.get("difficulty"), max);
            return cb.between(root.get("difficulty"), min, max);
        });
    }

    public static Specification<Question> hasDate(LocalDateTime start, LocalDateTime end){
        return ((root, query, cb) -> {
            if (start == null && end == null) return null;
            if (end == null) return cb.greaterThanOrEqualTo(root.get("createdAt"), start);
            if (start == null) return cb.lessThanOrEqualTo(root.get("createdAt"), end);
            return cb.between(root.get("createdAt"), start, end);
        });
    }

    public static Specification<Question> hasTag(String tag){
        return ((root, query, cb) -> {
            if (tag == null) return null;
            return cb.isMember(tag, root.get("tags"));
        });
    }
}
