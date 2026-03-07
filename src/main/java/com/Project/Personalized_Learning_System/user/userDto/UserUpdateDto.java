package com.Project.Personalized_Learning_System.user.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateDto(
        String username,

        @Email(flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid Email")
        String email
) {
}
