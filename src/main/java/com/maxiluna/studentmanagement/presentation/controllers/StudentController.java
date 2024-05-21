package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;
import com.maxiluna.studentmanagement.domain.usecases.student.*;
import com.maxiluna.studentmanagement.presentation.dtos.student.StudentRequestDto;
import com.maxiluna.studentmanagement.presentation.dtos.student.StudentResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private CreateStudentUseCase createStudentUseCase;

    @Autowired
    private GetStudentUseCase getStudentUseCase;

    @Autowired
    private GetStudentsBySubjectUseCase getStudentsBySubjectUseCase;

    @Autowired
    private UpdateStudentUseCase updateStudentUseCase;

    @Autowired
    private DeleteStudentUseCase deleteStudentUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> createStudent(@RequestBody @Valid StudentRequestDto studentToCreate) {
        Student student = studentToCreate.toStudent();
        Long subjectId = studentToCreate.getSubjectId();
        StudentStatus status = studentToCreate.getStatus();

        createStudentUseCase.execute(student, subjectId, status);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> getStudent(@PathVariable Long studentId) {
        Student student = getStudentUseCase.execute(studentId);

        return ResponseEntity.ok(StudentResponseDto.fromStudent(student));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<StudentResponseDto>> getStudentsBySubject(@PathVariable Long subjectId) {
        List<Student> studentList = getStudentsBySubjectUseCase.execute(subjectId);

        List<StudentResponseDto> studentListDto = studentList.stream()
                .map(StudentResponseDto::fromStudent)
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentListDto);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Void> updateStudent(@PathVariable Long studentId, @RequestBody @Valid StudentRequestDto updatedStudentDto) {
        Student updatedStudent = updatedStudentDto.toStudent();

        updateStudentUseCase.execute(studentId, updatedStudent);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        deleteStudentUseCase.execute(studentId);

        return ResponseEntity.noContent().build();
    }
}
