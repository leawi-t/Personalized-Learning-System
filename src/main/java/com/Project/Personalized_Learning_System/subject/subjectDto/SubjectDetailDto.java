package com.Project.Personalized_Learning_System.subject.subjectDto;

import com.Project.Personalized_Learning_System.topic.topicDto.TopicResponseDto;

import java.util.List;

public record SubjectDetailDto(
        Long id,
        String name,
        String description,
        List<TopicResponseDto> topics
) {}
