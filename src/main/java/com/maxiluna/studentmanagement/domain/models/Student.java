package com.maxiluna.studentmanagement.domain.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private Long id;

    @NotBlank(message = "FirstName must not be blank")
    @Size(min = 2, max = 100, message = "FirstName must be between 2 and 100 characters")
    private String firstName;

    @NotBlank(message = "LastName must not be blank")
    @Size(min = 2, max = 100, message = "LastName must be between 2 and 100 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    @Size(min = 2, max = 255, message = "Email must be between 2 and 255 characters")
    private String email;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Pattern(regexp = "[0-9]{8}", message = "DNI must be an 8-digit number")
    private String dni;

    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
    private String city;

    @Min(value = 2000, message = "Admission year must be greater than or equal to 2000")
    private Integer admissionYear;

    private List<Enrollment> enrollments = new ArrayList<>();

    private List<Attendance> attendances = new ArrayList<>();

    private List<Grade> grades = new ArrayList<>();
}
