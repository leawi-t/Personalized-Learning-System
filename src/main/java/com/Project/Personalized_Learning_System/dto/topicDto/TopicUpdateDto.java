package com.Project.Personalized_Learning_System.dto.topicDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TopicUpdateDto(
        @NotBlank
        String name,

        @Size(max = 1000)
        String description
) {
}
