package com.maxiluna.studentmanagement.presentation.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    @Size(min = 2, max = 255, message = "Email must be between 2 and 255 characters")
    String email;

    @NotBlank(message = "Password must not be blank")
    String password;
}
