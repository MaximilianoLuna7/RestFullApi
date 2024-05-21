package com.maxiluna.studentmanagement.presentation.dtos.enrollment;

import com.maxiluna.studentmanagement.domain.models.StudentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentRequestDto {
    @NotNull(message = "Student must not be null")
    private Long studentId;

    @NotNull(message = "Subject must not be null")
    private Long subjectId;

    private StudentStatus studentStatus;
}
