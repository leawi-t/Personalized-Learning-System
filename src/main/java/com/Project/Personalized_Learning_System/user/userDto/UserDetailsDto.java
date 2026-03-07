package com.Project.Personalized_Learning_System.user.userDto;

import com.Project.Personalized_Learning_System.subject.subjectDto.SubjectResponseDto;

import java.util.List;

public record UserDetailsDto(
        long id,
        String username,
        String email,
        List<SubjectResponseDto> subjects
) {
}
