package com.Project.Personalized_Learning_System.question.dto.questionDto;

import com.Project.Personalized_Learning_System.question.dto.choiceDto.UpdateChoiceDto;
import jakarta.validation.constraints.*;

import java.util.List;

public record QuestionUpdateDto(
        @Size(max = 1000, message = "Question can't be longer than 1000 characters")
        String questionText,

        @Min(value = 1, message = "Difficulty can not be lower than 0")
        @Max(value = 5, message = "Difficulty can not be higher than 5")
        int difficulty,

        String questionType,

        @Size(min=2, message = "There must be a minimum of 2 choices per questionText")
        List<UpdateChoiceDto> choices
) {}

