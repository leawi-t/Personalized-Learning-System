package com.Project.Personalized_Learning_System.dto.userDto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(

        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
