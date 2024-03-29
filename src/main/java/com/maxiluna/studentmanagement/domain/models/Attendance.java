package com.maxiluna.studentmanagement.domain.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
    private Long id;

    private LocalDate dateRecord;

    @NotNull(message = "Student must not be null")
    private Student student;

    @NotNull(message = "Subject must not be null")
    private Subject subject;

    @NotNull(message = "Class record must not be null")
    private ClassRecord classRecord;

    @NotNull(message = "Attendance status must not be null")
    private AttendanceStatus attendanceStatus;
}
