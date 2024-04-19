package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.ClassRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "class_records")
public class ClassRecordJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String topic;

    private String activities;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectJpa subject;

    @OneToMany(mappedBy = "classRecord", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AttendanceJpa> attendances;

    @OneToMany(mappedBy = "classRecord", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GradeJpa> grades;

    public static ClassRecordJpa fromClassRecord(ClassRecord classRecord) {
        return ClassRecordJpa.builder()
                .id(classRecord.getId())
                .topic(classRecord.getTopic())
                .activities(classRecord.getActivities())
                .date(classRecord.getDate())
                .build();
    }

    public ClassRecord toClassRecord() {
        return ClassRecord.builder()
                .id(this.id)
                .topic(this.topic)
                .activities(this.activities)
                .date(this.date)
                .subject(this.subject.toSubject())
                .build();
    }
}
