package com.Project.Personalized_Learning_System.subject;

import com.Project.Personalized_Learning_System.topic.TopicMapper;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectDetailDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectRequestDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectResponseDto;
import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = TopicMapper.class)
public interface SubjectMapper {

    SubjectResponseDto toResponse(Subject subject);

    List<SubjectResponseDto> toResponseList(List<Subject> subjects);

    SubjectDetailDto toDetail(Subject subject);

    Subject toEntity(SubjectRequestDto subjectRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubject(SubjectUpdateDto subjectUpdateDto, @MappingTarget Subject subject);
}
