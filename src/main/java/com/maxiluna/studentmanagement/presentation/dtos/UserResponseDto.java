package com.maxiluna.studentmanagement.presentation.dtos;

import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.models.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    @Size(min = 2, max = 255, message = "Email must be between 2 and 255 characters")
    private String email;

    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Role must not be null")
    private UserRole role;

    private List<SubjectDto> subjects = new ArrayList<>();

    public static UserResponseDto fromUser(User user) {
        List<SubjectDto> subjectsDto = user.getSubjects().stream()
                .map(SubjectDto::fromSubject)
                .collect(Collectors.toList());

        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .subjects(subjectsDto)
                .build();
    }
}
