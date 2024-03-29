package com.maxiluna.studentmanagement.infrastructure.entities;

import jakarta.persistence.*;
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
@Table(name = "grades")
public class GradeJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double score;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectJpa subject;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentJpa student;

    @ManyToOne
    @JoinColumn(name = "classRecord_id")
    private ClassRecordJpa classRecord;
}
