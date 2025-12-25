package com.Project.Personalized_Learning_System.dto.userDto;

import com.Project.Personalized_Learning_System.dto.categoryDto.CategoryResponseDto;

import java.util.List;

public record UserDetailsDto(
        long id,
        String username,
        String email,
        List<CategoryResponseDto> categories
) {
}
