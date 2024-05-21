package com.maxiluna.studentmanagement.presentation.dtos.attendance;

import com.maxiluna.studentmanagement.domain.models.Attendance;
import com.maxiluna.studentmanagement.domain.models.AttendanceStatus;
import com.maxiluna.studentmanagement.presentation.dtos.classRecord.ClassRecordResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.student.StudentResponseDto;
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
public class AttendanceResponseDto {
    private Long id;

    private LocalDate dateRecord;

    @NotNull(message = "Student must not be null")
    private StudentResponseDto student;

    @NotNull(message = "Class record must not be null")
    private ClassRecordResponseDto classRecord;

    @NotNull(message = "Attendance status must not be null")
    private AttendanceStatus attendanceStatus;

    public static AttendanceResponseDto fromAttendance(Attendance attendance) {
        return AttendanceResponseDto.builder()
                .id(attendance.getId())
                .dateRecord(attendance.getDateRecord())
                .student(StudentResponseDto.fromStudent(attendance.getStudent()))
                .classRecord(ClassRecordResponseDto.fromClassRecord(attendance.getClassRecord()))
                .attendanceStatus(attendance.getAttendanceStatus())
                .build();
    }
}
