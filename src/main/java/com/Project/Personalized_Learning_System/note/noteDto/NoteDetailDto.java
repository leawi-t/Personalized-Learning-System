package com.Project.Personalized_Learning_System.note.noteDto;

import java.time.LocalDateTime;

// TODO: make sure to fix this in the mapper class

public record NoteDetailDto(
        long id,
        long topicId,
        String topicName,
        String name,
        String description,
        String summary,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String fileName,
        String fileType,
        long fileSize
) {}

