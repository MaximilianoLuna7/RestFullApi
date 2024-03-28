package com.maxiluna.studentmanagement.presentation.dtos.subject;

import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.presentation.dtos.course.CourseResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.course.CreateCourseDto;
import com.maxiluna.studentmanagement.presentation.dtos.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResponseDto {
    private Long id;

    private String name;

    private Integer academicYear;

    private UserResponseDto teacher;

    private CourseResponseDto course;

    public static SubjectResponseDto fromSubject(Subject subject) {
        return SubjectResponseDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .academicYear(subject.getAcademicYear())
                .teacher(UserResponseDto.fromUser(subject.getTeacher()))
                .course(CourseResponseDto.fromCourse(subject.getCourse()))
                .build();
    }
}
