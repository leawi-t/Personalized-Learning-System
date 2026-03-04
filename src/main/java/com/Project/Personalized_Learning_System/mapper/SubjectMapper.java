package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.subjectDto.*;
import com.Project.Personalized_Learning_System.model.Subject;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = TopicMapper.class)
public interface SubjectMapper {

    SubjectResponseDto toResponse(Subject subject);

    SubjectDetailDto toDetail(Subject subject);

    Subject toEntity(SubjectRequestDto subjectRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubject(SubjectUpdateDto subjectUpdateDto, @MappingTarget Subject subject);
}
