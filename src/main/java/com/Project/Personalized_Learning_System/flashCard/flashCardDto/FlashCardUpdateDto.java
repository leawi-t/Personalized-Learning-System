package com.Project.Personalized_Learning_System.flashCard.flashCardDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public record FlashCardUpdateDto(
        @Size(max = 1000, message = "Question can not be longer than 1000 characters")
        String question,

        @Size(max = 1000, message = "Answer can not be longer than 1000 characters")
        String answer,

        @Size(min = 1, message = "Must at least have 1 tag")
        Set<String> tags,

        @Min(value = 1, message = "Difficulty must be at least one")
        @Max(value = 5, message = "Difficulty must be at most five")
        int difficulty
) {
}
