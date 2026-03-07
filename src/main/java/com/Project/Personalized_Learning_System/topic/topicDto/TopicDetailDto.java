package com.Project.Personalized_Learning_System.topic.topicDto;

import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardDetailDto;
import com.Project.Personalized_Learning_System.note.noteDto.NoteResponseDto;
import com.Project.Personalized_Learning_System.question.dto.questionDto.QuestionResponseDto;

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
