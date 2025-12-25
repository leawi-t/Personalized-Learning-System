package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.flashCardDto.*;
import com.Project.Personalized_Learning_System.model.FlashCard;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlashCardMapper {

    FlashCardDetailDto toDetail(FlashCard flashCard);

    List<FlashCardDetailDto> flashCardToDetail(List<FlashCard> flashCards);

    FlashCard toEntity(CreateFlashCardDto createFlashCardDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFlashCard(FlashCardUpdateDto updateDto, @MappingTarget FlashCard flashCard);
}
