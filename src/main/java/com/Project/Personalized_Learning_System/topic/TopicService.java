package com.Project.Personalized_Learning_System.topic;

import com.Project.Personalized_Learning_System.common.exception.customException.ResourceNotFoundException;
import com.Project.Personalized_Learning_System.subject.Subject;
import com.Project.Personalized_Learning_System.subject.SubjectService;
import com.Project.Personalized_Learning_System.topic.topicDto.TopicDetailDto;
import com.Project.Personalized_Learning_System.topic.topicDto.TopicRequestDto;
import com.Project.Personalized_Learning_System.topic.topicDto.TopicResponseDto;
import com.Project.Personalized_Learning_System.topic.topicDto.TopicUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepo repo;
    private final TopicMapper topicMapper;
    private final SubjectService subjectService;

    public Topic getTopicEntityById(long id){
        return repo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Topic not found"));
    }

    public Page<TopicResponseDto> getTopics(Pageable pageable, Long subjectId, String name, String description, LocalDateTime start, LocalDateTime end){
        Specification<Topic> spec = Specification.where(TopicSpecs.hasSubjectId(subjectId))
                .and(TopicSpecs.hasName(name))
                .and(TopicSpecs.hasDescription(description))
                .and(TopicSpecs.dateBetween(start, end));

        return repo.findAll(spec, pageable).map(topicMapper::toResponse);
    }

    public TopicDetailDto getTopicById(long topicId){
        return topicMapper.toDetail(repo.findById(topicId)
                .orElseThrow(()-> new ResourceNotFoundException("Topic not found")));
    }

    @Transactional
    public TopicDetailDto addTopic(TopicRequestDto dto){
        Subject subject = subjectService.getSubjectEntityById(dto.subjectId());
        Topic topic = topicMapper.toEntity(dto);

        subject.addTopic(topic);

        return topicMapper.toDetail(repo.save(topic));
    }

    @Transactional
    public TopicDetailDto updateTopic(TopicUpdateDto dto, long topicId){
        Topic topic = repo.findById(topicId)
                .orElseThrow(()->new ResourceNotFoundException("Topic not found"));
        topicMapper.updateTopic(dto, topic);
        return topicMapper.toDetail(repo.save(topic));
    }

    public void deleteTopicById(long id){
        if (!repo.existsById(id)){
            throw new ResourceNotFoundException("Topic not found");
        }
        repo.deleteById(id);
    }
}
