package com.maxiluna.studentmanagement.presentation.dtos.grade;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import com.maxiluna.studentmanagement.domain.models.Grade;
import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.presentation.dtos.classRecord.ClassRecordResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.student.StudentResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.subject.SubjectResponseDto;
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
public class GradeResponseDto {
    private Long id;

    private LocalDate recordDate;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 2, max = 500, message = "Description must be between 2 and 500 characters")
    private String description;

    @NotNull(message = "Score must not be null")
    @DecimalMin(value = "0", message = "Score must not be less than 0")
    @DecimalMax(value = "100", message = "Score must be greater than 100")
    private Double score;

    private SubjectResponseDto subject;

    private StudentResponseDto student;

    private ClassRecordResponseDto classRecord;

    public static GradeResponseDto fromGrade(Grade grade) {
        return GradeResponseDto.builder()
                .id(grade.getId())
                .recordDate(grade.getRecordDate())
                .description(grade.getDescription())
                .score(grade.getScore())
                .subject(SubjectResponseDto.fromSubject(grade.getSubject()))
                .student(StudentResponseDto.fromStudent(grade.getStudent()))
                .classRecord(ClassRecordResponseDto.fromClassRecord(grade.getClassRecord()))
                .build();
    }
}
