package com.Project.Personalized_Learning_System.dto.noteDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NoteUpdateDto(
        @NotBlank
        String name,

        @Size(max = 1000, message = "exceeded limit of 1000 characters")
        String description
) {
}
