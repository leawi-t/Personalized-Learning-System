package com.Project.Personalized_Learning_System.mapper;

import com.Project.Personalized_Learning_System.dto.topicDto.*;
import com.Project.Personalized_Learning_System.model.Topic;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {NoteMapper.class, QuestionMapper.class, FlashCardMapper.class})
public interface TopicMapper {

    TopicResponseDto toResponse(Topic topic);

    List<TopicResponseDto> topicToResponse(List<Topic> topics);

    TopicDetailDto toDetail(Topic topic);

    Topic toEntity(CreateTopicDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTopic(TopicUpdateDto dto, @MappingTarget Topic topic);
}
