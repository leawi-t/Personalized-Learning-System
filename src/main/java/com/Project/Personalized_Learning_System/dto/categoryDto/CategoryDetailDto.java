package com.Project.Personalized_Learning_System.dto.categoryDto;

import com.Project.Personalized_Learning_System.dto.topicDto.TopicResponseDto;

import java.util.List;

public record CategoryDetailDto(
        long id,
        String name,
        String description,
        List<TopicResponseDto> topics
) {}
