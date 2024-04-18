package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.Enrollment;
import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;
import com.maxiluna.studentmanagement.domain.models.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "enrollments")
public class EnrollmentJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentJpa student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectJpa subject;

    @Column(nullable = false, name = "student_status")
    private String studentStatus;

    public static EnrollmentJpa fromEnrollment(Enrollment enrollment) {
        return EnrollmentJpa.builder()
                .id(enrollment.getId())
                .studentStatus(enrollment.getStudentStatus().name())
                .build();
    }

    public Enrollment toEnrollment() {
        return Enrollment.builder()
                .id(this.id)
                .student(this.student.toStudent())
                .subject(this.subject.toSubject())
                .studentStatus(StudentStatus.valueOf(String.valueOf(this.studentStatus)))
                .build();
    }
}
