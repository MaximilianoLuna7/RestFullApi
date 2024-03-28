package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.domain.usecases.subject.*;
import com.maxiluna.studentmanagement.presentation.dtos.subject.SubjectRequestDto;
import com.maxiluna.studentmanagement.presentation.dtos.subject.SubjectResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private CreateSubjectUseCase createSubjectUseCase;

    @Autowired
    private GetSubjectUseCase getSubjectUseCase;

    @Autowired
    private UpdateSubjectUseCase updateSubjectUseCase;

    @Autowired
    private DeleteSubjectUseCase deleteSubjectUseCase;

    @Autowired
    private ListSubjectsUseCase listSubjectsUseCase;

    @Autowired
    private GetSubjectsByTeacherUseCase getSubjectsByTeacherUseCase;

    @Autowired
    private GetSubjectsByCourseUseCase getSubjectsByCourseUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> createSubject(@RequestBody @Valid SubjectRequestDto subjectToCreate) {
        Subject subject = subjectToCreate.toSubject();
        Long courseId = subjectToCreate.getCourseId();
        Long teacherId = subjectToCreate.getTeacherId();

        createSubjectUseCase.createSubject(subject, courseId, teacherId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDto> getSubject(@PathVariable Long subjectId) {
        Subject subject = getSubjectUseCase.getSubject(subjectId);

        return ResponseEntity.ok(SubjectResponseDto.fromSubject(subject));
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<Void> updateSubject(@PathVariable Long subjectId, @RequestBody @Valid SubjectRequestDto updatedSubjectDto) {
        Subject updatedSubject = updatedSubjectDto.toSubject();

        updateSubjectUseCase.updateSubject(subjectId, updatedSubject);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long subjectId) {
        deleteSubjectUseCase.deleteSubject(subjectId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> listSubjects() {
        List<Subject> subjectList = listSubjectsUseCase.listSubjects();

        List<SubjectResponseDto> subjectListDto = subjectList.stream()
                .map(SubjectResponseDto::fromSubject)
                .collect(Collectors.toList());

        return ResponseEntity.ok(subjectListDto);
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<SubjectResponseDto>> getSubjectsByTeacher(@PathVariable Long teacherId) {
        List<Subject> subjectList = getSubjectsByTeacherUseCase.getSubjectByTeacher(teacherId);

        List<SubjectResponseDto> subjectListDto = subjectList.stream()
                .map(SubjectResponseDto::fromSubject)
                .collect(Collectors.toList());

        return ResponseEntity.ok(subjectListDto);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<SubjectResponseDto>> getSubjectsByCourse(@PathVariable Long courseId) {
        List<Subject> subjectList = getSubjectsByCourseUseCase.getSubjectByCourse(courseId);

        List<SubjectResponseDto> subjectListDto = subjectList.stream()
                .map(SubjectResponseDto::fromSubject)
                .collect(Collectors.toList());

        return ResponseEntity.ok(subjectListDto);
    }
}
