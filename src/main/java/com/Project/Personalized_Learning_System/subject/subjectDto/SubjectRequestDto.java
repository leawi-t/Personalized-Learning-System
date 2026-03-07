package com.Project.Personalized_Learning_System.subject.subjectDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubjectRequestDto(
        @NotNull
        Long userId,

        @NotBlank
        @Size(max = 50, message = "Name can not be longer than 50 characters")
        String name,

        @Size(max = 1000, message = "Exceeded limit of 1000 characters")
        String description
) {}

