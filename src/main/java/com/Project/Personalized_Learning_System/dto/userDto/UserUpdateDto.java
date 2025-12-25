package com.Project.Personalized_Learning_System.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateDto(
        @NotBlank
        String username,

        @NotBlank
        @Email(flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid Email")
        String email
) {
}
