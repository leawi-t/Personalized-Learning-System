package com.Project.Personalized_Learning_System.note.noteDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NoteUpdateDto(
        @NotBlank
        String name,

        @Size(max = 1000, message = "exceeded limit of 1000 characters")
        String description,

        @Size(max = 2000, message = "exceeded limit of 2000 characters")
        String summary
) {
}
