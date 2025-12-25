package com.Project.Personalized_Learning_System.dto.choiceDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateChoiceDto(
        // may need id?
        @NotBlank
        @Size(max = 1000)
        String text,

        boolean correct
){}

