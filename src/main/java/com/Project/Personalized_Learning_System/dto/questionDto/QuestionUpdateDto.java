package com.Project.Personalized_Learning_System.dto.questionDto;

import com.Project.Personalized_Learning_System.dto.choiceDto.UpdateChoiceDto;
import com.Project.Personalized_Learning_System.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuestionUpdateDto(
        @NotBlank
        @Size(max = 1000)
        String questionText,

        @Size(min=1, max=5)
        int difficulty,

        @NotNull
        QuestionType questionType,

        @Size(min=2)
        List<UpdateChoiceDto> choices
) {}

