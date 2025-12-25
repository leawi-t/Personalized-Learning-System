package com.Project.Personalized_Learning_System.dto.choiceDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateChoiceDto(
        @NotBlank
        @Size(max = 1000, message = "exceeded the 1000 character limit")
        String text,

        boolean correct
) {}
