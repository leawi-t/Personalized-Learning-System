package com.Project.Personalized_Learning_System.question.mapper;

import com.Project.Personalized_Learning_System.question.dto.choiceDto.ChoiceResponseDto;
import com.Project.Personalized_Learning_System.question.dto.choiceDto.CreateChoiceDto;
import com.Project.Personalized_Learning_System.question.dto.choiceDto.UpdateChoiceDto;
import com.Project.Personalized_Learning_System.question.model.Choice;
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
