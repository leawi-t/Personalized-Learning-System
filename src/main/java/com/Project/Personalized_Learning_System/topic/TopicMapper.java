package com.Project.Personalized_Learning_System.topic;

import com.Project.Personalized_Learning_System.flashCard.FlashCardMapper;
import com.Project.Personalized_Learning_System.note.NoteMapper;
import com.Project.Personalized_Learning_System.question.mapper.QuestionMapper;
import com.Project.Personalized_Learning_System.subject.SubjectMapper;
import com.Project.Personalized_Learning_System.topic.topicDto.TopicDetailDto;
import com.Project.Personalized_Learning_System.topic.topicDto.TopicRequestDto;
import com.Project.Personalized_Learning_System.topic.topicDto.TopicResponseDto;
import com.Project.Personalized_Learning_System.topic.topicDto.TopicUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class, NoteMapper.class, QuestionMapper.class, FlashCardMapper.class})
public interface TopicMapper {

    TopicResponseDto toResponse(Topic topic);

    List<TopicResponseDto> toResponseList(List<Topic> topics);

    TopicDetailDto toDetail(Topic topic);

    Topic toEntity(TopicRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTopic(TopicUpdateDto dto, @MappingTarget Topic topic);
}
