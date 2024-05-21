package com.maxiluna.studentmanagement.presentation.dtos.grade;

import com.maxiluna.studentmanagement.domain.models.Grade;
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
public class GradeRequestDto {
    private LocalDate recordDate;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 2, max = 500, message = "Description must be between 2 and 500 characters")
    private String description;

    @NotNull(message = "Score must not be null")
    @DecimalMin(value = "0", message = "Score must not be less than 0")
    @DecimalMax(value = "100", message = "Score must be greater than 100")
    private Double score;

    private Long subjectId;

    private Long studentId;

    private Long classRecordId;

    public Grade toGrade() {
        return Grade.builder()
                .recordDate(this.recordDate)
                .description(this.description)
                .score(this.score)
                .build();
    }
}
