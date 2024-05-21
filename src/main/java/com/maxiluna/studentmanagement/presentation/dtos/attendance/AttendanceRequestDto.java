package com.maxiluna.studentmanagement.presentation.dtos.attendance;

import com.maxiluna.studentmanagement.domain.models.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceRequestDto {
    private Long studentId;

    private Long classRecordId;

    private Long subjectId;

    private AttendanceStatus attendanceStatus;
}
