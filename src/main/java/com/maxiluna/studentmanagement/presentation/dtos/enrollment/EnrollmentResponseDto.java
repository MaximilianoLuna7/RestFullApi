package com.maxiluna.studentmanagement.presentation.dtos.enrollment;

import com.maxiluna.studentmanagement.domain.models.Enrollment;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;
import com.maxiluna.studentmanagement.presentation.dtos.student.StudentResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.subject.SubjectResponseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponseDto {
    private Long id;

    @NotNull(message = "Student must not be null")
    private StudentResponseDto student;

    @NotNull(message = "Subject must not be null")
    private SubjectResponseDto subject;

    @NotNull(message = "Student status must not be null")
    private StudentStatus studentStatus;

    public static EnrollmentResponseDto fromEnrollment(Enrollment enrollment) {
        return EnrollmentResponseDto.builder()
                .id(enrollment.getId())
                .student(StudentResponseDto.fromStudent(enrollment.getStudent()))
                .subject(SubjectResponseDto.fromSubject(enrollment.getSubject()))
                .studentStatus(enrollment.getStudentStatus())
                .build();
    }
}
