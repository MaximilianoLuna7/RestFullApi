package com.maxiluna.studentmanagement.presentation.dtos.course;

import com.maxiluna.studentmanagement.domain.models.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {

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

    public Course toCourse() {
        Course course = new Course();
        course.setName(this.name);
        course.setInstitutionName(this.institutionName);
        course.setDurationInYears(this.durationInYears);

        return course;
    }

    public static CourseDto fromCourse(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .institutionName(course.getInstitutionName())
                .durationInYears(course.getDurationInYears())
                .build();
    }
}
