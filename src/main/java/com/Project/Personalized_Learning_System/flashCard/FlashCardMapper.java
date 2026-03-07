package com.Project.Personalized_Learning_System.flashCard;

import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardDetailDto;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardRequestDto;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardResponseDto;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardUpdateDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FlashCardMapper {

    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "topic.name", target = "topicName")
    FlashCardDetailDto toDetail(FlashCard flashCard);

    FlashCardResponseDto toResponse(FlashCard flashCard);

    FlashCard toEntity(FlashCardRequestDto flashCardRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFlashCard(FlashCardUpdateDto updateDto, @MappingTarget FlashCard flashCard);
}
