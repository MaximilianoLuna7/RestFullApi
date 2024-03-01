package com.maxiluna.studentmanagement.presentation.dtos;

import com.maxiluna.studentmanagement.domain.models.Subject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {

    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Academic year most not be null")
    @Min(value = 2000, message = "Academic year must be greater than or equal to 2000")
    private Integer academicYear;

    private Long teacherId;

    public static SubjectDto fromSubject(Subject subject) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .academicYear(subject.getAcademicYear())
                .teacherId(subject.getTeacher().getId())
                .build();
    }

    public UserResponseDto getTeacherDto(Subject subject) {
        return UserResponseDto.fromUser(subject.getTeacher());
    }
}

