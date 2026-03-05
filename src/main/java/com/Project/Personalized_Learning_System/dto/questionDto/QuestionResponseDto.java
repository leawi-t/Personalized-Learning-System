package com.Project.Personalized_Learning_System.dto.questionDto;

import com.Project.Personalized_Learning_System.dto.choiceDto.ChoiceResponseDto;
import com.Project.Personalized_Learning_System.model.QuestionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record QuestionResponseDto(
        long id,
        String questionText,
        QuestionType questionType,
        int difficulty,
        Set<String> tags,
        List<ChoiceResponseDto> choices,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

