package com.Project.Personalized_Learning_System.dto.flashCardDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record FlashCardUpdateDto(
        @NotBlank
        @Length(max = 1000)
        String question,

        @NotBlank
        @Length(max = 1000)
        String answer,

        String tags,

        @Size(min=1, max=5)
        int difficulty
) {
}
