package com.Project.Personalized_Learning_System.dto.subjectDto;

import com.Project.Personalized_Learning_System.dto.topicDto.TopicResponseDto;

import java.util.List;

public record SubjectDetailDto(
        Long id,
        String name,
        String description,
        List<TopicResponseDto> topics
) {}
