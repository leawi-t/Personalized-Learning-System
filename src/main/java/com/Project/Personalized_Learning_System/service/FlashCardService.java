package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.flashCardDto.*;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.FlashCardMapper;
import com.Project.Personalized_Learning_System.model.FlashCard;
import com.Project.Personalized_Learning_System.model.Topic;
import com.Project.Personalized_Learning_System.repository.FlashCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashCardService {

    FlashCardRepo repo;
    FlashCardMapper mapper;
    TopicService topicService;

    @Autowired
    public FlashCardService(FlashCardRepo repo, FlashCardMapper mapper, TopicService topicService){
        this.repo = repo;
        this.mapper = mapper;
        this.topicService = topicService;
    }

    public List<FlashCardDetailDto> getAllFlashCards(){
        return mapper.flashCardToDetail(repo.findAll());
    }

    public FlashCardDetailDto getFlashCardById(long id){
        return mapper.toDetail(repo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("FlashCard not found")));
    }

    public List<FlashCardDetailDto> getFlashCardByTopic(long topicId){
        return mapper.flashCardToDetail(repo.findByTopicId(topicId));
    }

    public List<FlashCardDetailDto> searchFlashCard(String keyword){
        return mapper.flashCardToDetail(repo.searchFlashCard(keyword));
    }

    public FlashCardDetailDto addFlashCard(CreateFlashCardDto createFlashCardDto, long topicId){
        Topic topic = topicService.getTopicEntityById(topicId);
        FlashCard flashCard = mapper.toEntity(createFlashCardDto);

        flashCard.setTopic(topic);
        return mapper.toDetail(repo.save(flashCard));
    }

    public FlashCardDetailDto updateFlashCard(FlashCardUpdateDto updateDto, long flashCardId){
        FlashCard flashCard = repo.findById(flashCardId)
                .orElseThrow(() -> new ResourceNotFoundException("FlashCard not found"));

        mapper.updateFlashCard(updateDto, flashCard);
        return mapper.toDetail(repo.save(flashCard));
    }

    public void deleteById(long flashCardId){
        if (!repo.existsById(flashCardId)){
            throw new ResourceNotFoundException("FlashCard not found");
        }

        repo.deleteById(flashCardId);
    }
}
