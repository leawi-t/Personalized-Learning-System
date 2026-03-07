package com.Project.Personalized_Learning_System.note;

import com.Project.Personalized_Learning_System.note.noteDto.NoteDetailDto;
import com.Project.Personalized_Learning_System.note.noteDto.NoteResponseDto;
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
