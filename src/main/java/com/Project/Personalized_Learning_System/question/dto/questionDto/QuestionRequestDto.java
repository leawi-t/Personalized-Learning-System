package com.Project.Personalized_Learning_System.question.dto.questionDto;

import com.Project.Personalized_Learning_System.question.dto.choiceDto.CreateChoiceDto;
import jakarta.validation.constraints.*;

import java.util.List;

public record QuestionRequestDto(

        @NotNull
        long topicId,

        @NotBlank
        @Size(max = 1000, message = "Question text can not be larger than 1000 characters")
        String questionText,

        @NotNull
        String questionType,

        @Min(value = 1, message = "Question difficulty can not be smaller than 1")
        @Max(value = 5, message = "Question difficulty can not be larger than 5")
        int difficulty,

        @NotNull
        @Size(min = 2, max = 5, message = "Question must have between 2 and 5 choices")
        List<CreateChoiceDto> choices
) {}

