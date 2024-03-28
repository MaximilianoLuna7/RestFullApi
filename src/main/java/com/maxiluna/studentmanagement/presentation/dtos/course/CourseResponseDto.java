package com.maxiluna.studentmanagement.presentation.dtos.course;

import com.maxiluna.studentmanagement.domain.models.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseDto {
    private Long id;

    private String name;

    private String institutionName;

    private Double durationInYears;

    public static CourseResponseDto fromCourse(Course course) {
        return CourseResponseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .institutionName(course.getInstitutionName())
                .durationInYears(course.getDurationInYears())
                .build();
    }
}
