package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.choiceDto.*;
import com.Project.Personalized_Learning_System.model.Choice;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {

    ChoiceResponseDto toResponse(Choice choice);

    List<ChoiceResponseDto> toResponseList(List<Choice> choice);

    Choice toEntity(CreateChoiceDto choiceDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateChoice(UpdateChoiceDto choiceDto, @MappingTarget Choice choice);
}
