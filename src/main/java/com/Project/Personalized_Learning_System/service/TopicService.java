package com.Project.Personalized_Learning_System.service;

import com.Project.Personalized_Learning_System.dto.topicDto.*;
import com.Project.Personalized_Learning_System.exception.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.mapper.TopicMapper;
import com.Project.Personalized_Learning_System.model.Subject;
import com.Project.Personalized_Learning_System.model.Topic;
import com.Project.Personalized_Learning_System.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepo repo;
    private final TopicMapper topicMapper;
    private final SubjectService subjectService;

    @Autowired
    public TopicService(TopicRepo repo, TopicMapper topicMapper, SubjectService subjectService){
        this.repo = repo;
        this.topicMapper = topicMapper;
        this.subjectService = subjectService;
    }

    public Topic getTopicEntityById(long id){
        return repo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Topic not found"));
    }

    public List<TopicResponseDto> getAllTopics(){
        return topicMapper.topicToResponse(repo.findAll());
    }

    public TopicDetailDto getTopicById(long topicId){
        return topicMapper.toDetail(repo.findById(topicId)
                .orElseThrow(()-> new ResourceNotFoundException("Topic not found")));
    }

    public List<TopicResponseDto> getTopicByCategory(long categoryId){
        return topicMapper.topicToResponse(repo.findByCategoryId(categoryId));
    }

    public List<TopicResponseDto> searchTopic (String keyword){
        return topicMapper.topicToResponse(repo.searchTopic(keyword));
    }

    public TopicDetailDto addTopic(CreateTopicDto createTopicDto, long categoryId){
        System.out.println("Multipart request reached NoteService");
        Subject subject = subjectService.getCategoryEntityById(categoryId);
        Topic topic = topicMapper.toEntity(createTopicDto);
        topic.setSubject(subject);
        return topicMapper.toDetail(repo.save(topic));
    }

    public TopicDetailDto updateTopic(TopicUpdateDto topicUpdateDto, long id){
        Topic topic = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Topic not found"));
        topicMapper.updateTopic(topicUpdateDto, topic);
        return topicMapper.toDetail(repo.save(topic));
    }

    public void deleteTopicById(long id){
        if (!repo.existsById(id)){
            throw new ResourceNotFoundException("Topic not found");
        }
        repo.deleteById(id);
    }
}
