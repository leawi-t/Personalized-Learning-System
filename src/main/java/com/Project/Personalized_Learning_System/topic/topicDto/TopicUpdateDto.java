package com.Project.Personalized_Learning_System.topic.topicDto;

import jakarta.validation.constraints.Size;

public record TopicUpdateDto(

        @Size(max = 50, message = "Name can not be longer than 50 characters")
        String name,

        @Size(max = 1000, message = "Description can not be longer than 1000 characters")
        String description
) {
}
