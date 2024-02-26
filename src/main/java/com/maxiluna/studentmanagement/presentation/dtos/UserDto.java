package com.maxiluna.studentmanagement.presentation.dtos;

import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.models.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    @Size(min = 2, max = 255, message = "Email must be between 2 and 255 characters")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Role must not be null")
    private UserRole role;

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .birthDate(this.birthDate)
                .role(this.role)
                .build();
    }
}
