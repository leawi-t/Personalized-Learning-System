package com.Project.Personalized_Learning_System.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserDto(

        @NotBlank
        String username,

        @NotBlank
        @Email(flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid Email")
        String email,

        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=.,?])(?=\\S+$).{8,20}$",
                message = "Password must be 8-20 characters long and include at least one digit, " +
                        "one lowercase letter, one uppercase letter, and one special character.")
        String password
) {
}
