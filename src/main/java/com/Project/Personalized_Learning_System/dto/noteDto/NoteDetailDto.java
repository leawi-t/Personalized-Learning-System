package com.Project.Personalized_Learning_System.dto.noteDto;

import java.time.LocalDateTime;

public record NoteDetailDto(
        long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String fileName,
        String fileType,
        long fileSize
) {}

