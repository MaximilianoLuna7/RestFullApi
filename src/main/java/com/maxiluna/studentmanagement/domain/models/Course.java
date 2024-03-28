package com.maxiluna.studentmanagement.domain.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Institution name must not be blank")
    @Size(min = 2, max = 100, message = "Institution name must be between 2 and 100 characters")
    private String institutionName;

    @NotNull(message = "Duration years most not be null")
    @Positive(message = "Duration years must be a positive number")
    private Double durationInYears;

    private List<Subject> subjects;
}
