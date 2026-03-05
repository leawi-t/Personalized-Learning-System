package com.Project.Personalized_Learning_System.dto.topicDto;

import com.Project.Personalized_Learning_System.dto.flashCardDto.FlashCardDetailDto;
import com.Project.Personalized_Learning_System.dto.noteDto.NoteResponseDto;
import com.Project.Personalized_Learning_System.dto.questionDto.QuestionResponseDto;

import java.util.List;

public record TopicDetailDto(
        long id,
        String name,
        String description,
        long subjectId,
        String summary,
        List<NoteResponseDto> notes,
        List<QuestionResponseDto> questions,
        List<FlashCardDetailDto> flashCards
) {
}
