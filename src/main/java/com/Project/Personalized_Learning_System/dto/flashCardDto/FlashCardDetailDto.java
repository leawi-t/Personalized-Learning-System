package com.Project.Personalized_Learning_System.dto.flashCardDto;

import java.time.LocalDateTime;
import java.util.Set;

public record FlashCardDetailDto(
        long id,
        long topicId,
        String topicName,
        String question,
        String answer,
        Set<String> tags,
        int difficulty,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
