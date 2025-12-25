package com.Project.Personalized_Learning_System.dto.questionDto;

import com.Project.Personalized_Learning_System.dto.choiceDto.ChoiceDetailDto;
import com.Project.Personalized_Learning_System.model.QuestionType;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionDetailsDto(
        long id,
        String questionText,
        QuestionType questionType,
        int difficulty,
        List<ChoiceDetailDto> choices,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

