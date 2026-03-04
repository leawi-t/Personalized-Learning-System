package com.Project.Personalized_Learning_System.dto.subjectDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubjectUpdateDto(
        String name,
        @Size(max = 255, message = "exceeded limit of 255 characters")
        String description
) {}

