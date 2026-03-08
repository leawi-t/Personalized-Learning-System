package com.Project.Personalized_Learning_System.question.dto.questionDto;

import com.Project.Personalized_Learning_System.question.dto.choiceDto.ChoiceResponseDto;
import com.Project.Personalized_Learning_System.question.model.QuestionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record QuestionResponseDto(
        long id,
        String questionText,
        QuestionType questionType,
        String explanation,
        int difficulty,
        Set<String> tags,
        List<ChoiceResponseDto> choices,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

