package com.maxiluna.studentmanagement.domain.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    private Long id;

    @NotNull(message = "Student must not be null")
    private Student student;

    @NotNull(message = "Subject must not be null")
    private Subject subject;

    @NotNull(message = "Student status must not be null")
    private StudentStatus studentStatus;
}
