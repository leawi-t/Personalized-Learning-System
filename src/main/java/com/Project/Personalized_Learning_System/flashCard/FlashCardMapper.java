package com.Project.Personalized_Learning_System.flashCard;

import com.Project.Personalized_Learning_System.flashCard.flashCardDto.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlashCardMapper {

    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "topic.name", target = "topicName")
    FlashCardDetailDto toDetail(FlashCard flashCard);

    FlashCardResponseDto toResponse(FlashCard flashCard);

    FlashCard toEntity(FlashCardRequestDto flashCardRequestDto);

    List<FlashCard> toEntityAI(List<FlashCardAiResponse> flashCardAiResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFlashCard(FlashCardUpdateDto updateDto, @MappingTarget FlashCard flashCard);
}
