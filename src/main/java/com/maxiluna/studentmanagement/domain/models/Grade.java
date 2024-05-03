package com.maxiluna.studentmanagement.domain.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grade {
    private Long id;

    private LocalDate recordDate;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 2, max = 500, message = "Description must be between 2 and 500 characters")
    private String description;

    @NotNull(message = "Score must not be null")
    @DecimalMin(value = "0", message = "Score must not be less than 0")
    @DecimalMax(value = "100", message = "Score must be greater than 100")
    private Double score;

    private Subject subject;

    private Student student;

    private ClassRecord classRecord;
}
