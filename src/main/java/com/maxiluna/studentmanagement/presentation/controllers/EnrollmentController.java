package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.Enrollment;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;
import com.maxiluna.studentmanagement.domain.usecases.enrollment.*;
import com.maxiluna.studentmanagement.presentation.dtos.enrollment.EnrollmentRequestDto;
import com.maxiluna.studentmanagement.presentation.dtos.enrollment.EnrollmentResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    @Autowired
    private CreateEnrollmentUseCase createEnrollmentUseCase;

    @Autowired
    private GetEnrollmentUseCase getEnrollmentUseCase;

    @Autowired
    private ListEnrollmentsBySubjectUseCase listEnrollmentsBySubjectUseCase;

    @Autowired
    private UpdateEnrollmentStatusUseCase updateEnrollmentStatusUseCase;

    @Autowired
    private DeleteEnrollmentUseCase deleteEnrollmentUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> createEnrollment(@RequestBody @Valid EnrollmentRequestDto enrollmentToCreate) {
        Long studentId = enrollmentToCreate.getStudentId();
        Long subjectId = enrollmentToCreate.getSubjectId();
        StudentStatus status = enrollmentToCreate.getStudentStatus();

        createEnrollmentUseCase.execute(studentId, subjectId, status);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<EnrollmentResponseDto> getEnrollment(@RequestBody EnrollmentRequestDto enrollmentRequest) {
        Long studentId = enrollmentRequest.getStudentId();
        Long subjectId = enrollmentRequest.getSubjectId();

        Enrollment enrollment = getEnrollmentUseCase.execute(studentId, subjectId);

        return ResponseEntity.ok(EnrollmentResponseDto.fromEnrollment(enrollment));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsBySubject(@PathVariable Long subjectId) {
        List<Enrollment> enrollmentList = listEnrollmentsBySubjectUseCase.execute(subjectId);

        List<EnrollmentResponseDto> enrollmentListDto = enrollmentList.stream()
                .map(EnrollmentResponseDto::fromEnrollment)
                .collect(Collectors.toList());

        return ResponseEntity.ok(enrollmentListDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateEnrollment(@RequestBody EnrollmentRequestDto updateRequest) {
        Long studentId = updateRequest.getStudentId();
        Long subjectId = updateRequest.getSubjectId();
        StudentStatus status = updateRequest.getStudentStatus();

        updateEnrollmentStatusUseCase.execute(studentId,subjectId, status);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEnrollment(@RequestBody EnrollmentRequestDto deleteRequest) {
        Long studentId = deleteRequest.getStudentId();
        Long subjectId = deleteRequest.getSubjectId();

        deleteEnrollmentUseCase.execute(studentId, subjectId);

        return ResponseEntity.noContent().build();
    }
}
