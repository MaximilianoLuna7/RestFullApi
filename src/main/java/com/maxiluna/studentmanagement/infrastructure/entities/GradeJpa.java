package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.Grade;
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
    @JoinColumn(name = "student_id")
    private StudentJpa student;

    @ManyToOne
    @JoinColumn(name = "classRecord_id")
    private ClassRecordJpa classRecord;

    public static GradeJpa fromGrade(Grade grade) {
        return GradeJpa.builder()
                .id(grade.getId())
                .recordDate(grade.getRecordDate())
                .description(grade.getDescription())
                .score(grade.getScore())
                .build();
    }

    public Grade toGrade() {
        return Grade.builder()
                .id(this.id)
                .recordDate(this.recordDate)
                .description(this.description)
                .score(this.score)
                .student(this.toGrade().getStudent())
                .classRecord(this.classRecord.toClassRecord())
                .build();
    }
}
