package com.Project.Personalized_Learning_System.dto.questionDto;

import java.time.LocalDateTime;

public record FilterQuestionDto(
        Long topicId,
        String type,
        String questionText,
        Integer min,
        Integer max,
        LocalDateTime start,
        LocalDateTime end,
        String tag
) {
}
