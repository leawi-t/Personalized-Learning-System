package com.Project.Personalized_Learning_System.dto.flashCardDto;

import java.time.LocalDateTime;

public record FlashCardDetailDto(
        long id,
        String question,
        String answer,
        String tags,
        int difficulty,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
