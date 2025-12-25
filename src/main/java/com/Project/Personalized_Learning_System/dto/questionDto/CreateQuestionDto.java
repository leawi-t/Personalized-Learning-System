package com.Project.Personalized_Learning_System.dto.questionDto;

import com.Project.Personalized_Learning_System.dto.choiceDto.CreateChoiceDto;
import com.Project.Personalized_Learning_System.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateQuestionDto(
        @NotBlank
        @Size(max = 1000)
        String questionText,

        @NotNull
        QuestionType questionType,

        @Size(min=1, max=5)
        int difficulty,

        @NotNull
        @Size(min = 2)
        List<CreateChoiceDto> choices
) {}

