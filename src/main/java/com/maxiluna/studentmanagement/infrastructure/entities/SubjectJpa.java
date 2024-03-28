package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.Subject;
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
@Table(name = "subjects")
public class SubjectJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "academic_year")
    private Integer academicYear;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseJpa course;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserJpa teacher;

    public static SubjectJpa fromSubject(Subject subject) {
        return SubjectJpa.builder()
                .id(subject.getId())
                .name(subject.getName())
                .academicYear(subject.getAcademicYear())
                .build();
    }

    public Subject toSubject() {
        return Subject.builder()
                .id(this.id)
                .name(this.name)
                .academicYear(this.academicYear)
                .course(this.course.toCourse())
                .teacher(this.teacher.toUser())
                .build();
    }
}
