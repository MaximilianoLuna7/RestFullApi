package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.AttendanceStatus;
import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.models.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attendances")
public class AttendanceJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_record")
    private LocalDate dateRecord;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentJpa student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectJpa subject;

    @ManyToOne
    @JoinColumn(name = "classRecord_id")
    private ClassRecordJpa classRecord;

    @Column(nullable = false, name = "attendance_status")
    private String attendanceStatus;
}
