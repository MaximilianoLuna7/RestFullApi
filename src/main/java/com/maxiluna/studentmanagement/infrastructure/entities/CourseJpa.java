package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class CourseJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "institution_name")
    private String institutionName;

    @Column(nullable = false, name = "duration_in_years")
    private Double durationInYears;

    public static CourseJpa fromCourse(Course course) {
        return CourseJpa.builder()
                .id(course.getId())
                .name(course.getName())
                .institutionName(course.getInstitutionName())
                .durationInYears(course.getDurationInYears())
                .build();
    }

    public Course toCourse() {
        return Course.builder()
                .id(this.id)
                .name(this.name)
                .institutionName(this.getInstitutionName())
                .durationInYears(this.durationInYears)
                .build();
    }
}
