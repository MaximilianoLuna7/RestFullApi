package com.maxiluna.studentmanagement.infrastructure.entities;

import com.maxiluna.studentmanagement.domain.models.*;
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
    @JoinColumn(name = "classRecord_id")
    private ClassRecordJpa classRecord;

    @Column(nullable = false, name = "attendance_status")
    private String attendanceStatus;

    public static AttendanceJpa fromAttendance(Attendance attendance) {
        return AttendanceJpa.builder()
                .id(attendance.getId())
                .dateRecord(attendance.getDateRecord())
                .attendanceStatus(attendance.getAttendanceStatus().name())
                .build();
    }

    public Attendance toAttendance() {
        return Attendance.builder()
                .id(this.id)
                .dateRecord(this.dateRecord)
                .student(this.student.toStudent())
                .classRecord(this.classRecord.toClassRecord())
                .attendanceStatus(AttendanceStatus.valueOf(String.valueOf(this.attendanceStatus)))
                .build();
    }
}
