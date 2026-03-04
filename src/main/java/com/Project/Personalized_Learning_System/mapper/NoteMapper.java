package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.noteDto.*;
import com.Project.Personalized_Learning_System.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteResponseDto toResponse(Note note);

    List<NoteResponseDto> noteToResponse(List<Note> notes);

    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "topic.name", target = "topicName")
    NoteDetailDto toDetails(Note note);
}
