package com.Project.Personalized_Learning_System.dto.topicDto;

import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardDetailDto;
import com.Project.Personalized_Learning_System.dto.noteDto.NoteResponseDto;
import com.Project.Personalized_Learning_System.dto.questionDto.QuestionDetailsDto;

import java.util.List;

public record TopicDetailDto(
        long id,
        String name,
        String description,
        long categoryId,
        List<NoteResponseDto> notes,
        List<QuestionDetailsDto> questions,
        List<FlashCardDetailDto> flashCards
) {
}
