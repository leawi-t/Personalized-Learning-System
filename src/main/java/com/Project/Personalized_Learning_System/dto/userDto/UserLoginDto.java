package com.Project.Personalized_Learning_System.dto.userDto;

import jakarta.validation.constraints.NotBlank;

// for future security
public record UserLoginDto(

        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
