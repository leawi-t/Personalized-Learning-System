package com.Project.Personalized_Learning_System.flashCard;

import com.Project.Personalized_Learning_System.common.exception.customException.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardDetailDto;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardRequestDto;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardResponseDto;
import com.Project.Personalized_Learning_System.flashCard.flashCardDto.FlashCardUpdateDto;
import com.Project.Personalized_Learning_System.topic.Topic;
import com.Project.Personalized_Learning_System.topic.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlashCardService {

    private final FlashCardRepo repo;
    private final FlashCardMapper mapper;
    private final TopicService topicService;

    public Page<FlashCardResponseDto> getFlashCards(Long topicId, String tag, Integer min, Integer max,
                                                    LocalDateTime start, LocalDateTime end, Pageable pageable){
        Specification<FlashCard> spec = Specification.where(FlashCardSpecs.hasTopicId(topicId))
                .and(FlashCardSpecs.hasTag(tag))
                .and(FlashCardSpecs.hasDate(start, end))
                .and(FlashCardSpecs.hasDifficulty(min, max));

        return repo.findAll(spec, pageable).map(mapper::toResponse);
    }

    public FlashCardDetailDto getFlashCardById(long id){
        return mapper.toDetail(repo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("FlashCard not found")));
    }

    @Transactional
    public FlashCardDetailDto addFlashCard(FlashCardRequestDto dto){
        Topic topic = topicService.getTopicEntityById(dto.topicId());
        FlashCard flashCard = mapper.toEntity(dto);
        topic.addFlashCard(flashCard);
        return mapper.toDetail(repo.save(flashCard));
    }

    @Transactional
    public FlashCardDetailDto updateFlashCard(FlashCardUpdateDto updateDto, long flashCardId){
        FlashCard flashCard = repo.findById(flashCardId)
                .orElseThrow(() -> new ResourceNotFoundException("FlashCard not found"));

        mapper.updateFlashCard(updateDto, flashCard);
        return mapper.toDetail(repo.save(flashCard));
    }

    @Transactional
    public void deleteById(long flashCardId){
        if (!repo.existsById(flashCardId)){
            throw new ResourceNotFoundException("FlashCard not found");
        }

        repo.deleteById(flashCardId);
    }
}
