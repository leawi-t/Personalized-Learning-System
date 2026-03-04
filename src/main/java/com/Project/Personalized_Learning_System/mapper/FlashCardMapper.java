package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.flashCardDto.*;
import com.Project.Personalized_Learning_System.model.FlashCard;
import com.Project.Personalized_Learning_System.service.FlashCardSpecs;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlashCardMapper {

    FlashCardDetailDto toDetail(FlashCard flashCard);

    FlashCardResponseDto toResponse(FlashCard flashCard);

    @Mapping(target = "topic.id", source = "topicId")
    @Mapping(target = "topic.name", source = "topicName")
    FlashCard toEntity(FlashCardRequestDto flashCardRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFlashCard(FlashCardUpdateDto updateDto, @MappingTarget FlashCard flashCard);
}
