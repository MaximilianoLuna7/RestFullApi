package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.Attendance;
import com.maxiluna.studentmanagement.domain.models.AttendanceStatus;
import com.maxiluna.studentmanagement.domain.usecases.attendance.*;
import com.maxiluna.studentmanagement.presentation.dtos.attendance.AttendanceRequestDto;
import com.maxiluna.studentmanagement.presentation.dtos.attendance.AttendanceResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {
    @Autowired
    private CreateAttendanceUseCase createAttendanceUseCase;

    @Autowired
    private GetAttendanceUseCase getAttendanceUseCase;

    @Autowired
    private GetAttendancesByStudentAndSubjectUseCase getAttendancesByStudentAndSubjectUseCase;

    @Autowired
    private GetAttendancesByClassRecordUseCase getAttendancesByClassRecordUseCase;

    @Autowired
    private UpdateAttendanceStatusUseCase updateAttendanceStatusUseCase;

    @Autowired
    private DeleteAttendanceUseCase deleteAttendanceUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> createAttendance(@RequestBody AttendanceRequestDto attendanceRequest) {
        Long studentId = attendanceRequest.getStudentId();
        Long classRecordId = attendanceRequest.getClassRecordId();
        AttendanceStatus status = attendanceRequest.getAttendanceStatus();

        createAttendanceUseCase.execute(studentId, classRecordId, status);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{attendanceId}")
    public ResponseEntity<AttendanceResponseDto> getAttendance(@PathVariable Long attendanceId) {
        Attendance attendance = getAttendanceUseCase.execute(attendanceId);

        return ResponseEntity.ok(AttendanceResponseDto.fromAttendance(attendance));
    }

    @GetMapping("/get")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByStudentAndSubject(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        Long studentId = attendanceRequestDto.getStudentId();
        Long subjectId = attendanceRequestDto.getSubjectId();

        List<Attendance> attendanceList = getAttendancesByStudentAndSubjectUseCase.execute(studentId, subjectId);

        List<AttendanceResponseDto> attendanceListDto = attendanceList.stream()
                .map(AttendanceResponseDto::fromAttendance)
                .collect(Collectors.toList());

        return ResponseEntity.ok(attendanceListDto);
    }

    @GetMapping("/get/classRecord/{classRecordId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByClassRecord(@PathVariable Long classRecordId) {
        List<Attendance> attendanceList = getAttendancesByClassRecordUseCase.execute(classRecordId);

        List<AttendanceResponseDto> attendanceListDto = attendanceList.stream()
                .map(AttendanceResponseDto::fromAttendance)
                .collect(Collectors.toList());

        return ResponseEntity.ok(attendanceListDto);
    }

    @PutMapping("/update/{attendanceId}")
    public ResponseEntity<Void> updateAttendanceStatus(@PathVariable Long attendanceId, @RequestBody AttendanceRequestDto updatedAttendance) {
        AttendanceStatus status = updatedAttendance.getAttendanceStatus();

        updateAttendanceStatusUseCase.execute(attendanceId, status);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        deleteAttendanceUseCase.execute(attendanceId);

        return ResponseEntity.noContent().build();
    }
}
