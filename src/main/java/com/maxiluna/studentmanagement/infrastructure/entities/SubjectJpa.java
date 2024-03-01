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
        Subject subject = Subject.builder()
                .id(this.id)
                .name(this.name)
                .academicYear(this.academicYear)
                .build();

        if (this.teacher != null) {
            subject.setTeacher(this.teacher.toUser());
        }

        return subject;
    }
}
