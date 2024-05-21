package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.Grade;
import com.maxiluna.studentmanagement.domain.usecases.grade.*;
import com.maxiluna.studentmanagement.presentation.dtos.grade.GradeRequestDto;
import com.maxiluna.studentmanagement.presentation.dtos.grade.GradeResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    @Autowired
    private CreateGradeUseCase createGradeUseCase;

    @Autowired
    private GetGradeUseCase getGradeUseCase;

    @Autowired
    private GetGradesByStudentAndSubjectUseCase getGradesByStudentAndSubjectUseCase;

    @Autowired
    private UpdateGradeUseCase updateGradeUseCase;

    @Autowired
    private DeleteGradeUseCase deleteGradeUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> createGrade(@RequestBody @Valid GradeRequestDto gradeToCreate) {
        Long studentId = gradeToCreate.getStudentId();
        Long classRecordId = gradeToCreate.getClassRecordId();
        Grade grade = gradeToCreate.toGrade();

        createGradeUseCase.execute(grade, studentId, classRecordId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{gradeId}")
    public ResponseEntity<GradeResponseDto> getGrade(@PathVariable Long gradeId) {
        Grade grade = getGradeUseCase.execute(gradeId);

        return ResponseEntity.ok(GradeResponseDto.fromGrade(grade));
    }

    @GetMapping("/get")
    public ResponseEntity<List<GradeResponseDto>> getGradesByStudentAndSubject(@RequestBody GradeRequestDto gradeRequestDto) {
        Long studentId = gradeRequestDto.getStudentId();
        Long subjectId = gradeRequestDto.getSubjectId();

        List<Grade> gradeList = getGradesByStudentAndSubjectUseCase.execute(studentId, subjectId);

        List<GradeResponseDto> gradeListDto = gradeList.stream()
                .map(GradeResponseDto::fromGrade)
                .collect(Collectors.toList());

        return ResponseEntity.ok(gradeListDto);
    }

    @PutMapping("/update/{gradeId}")
    public ResponseEntity<Void> updateGrade(@PathVariable Long gradeId, @RequestBody @Valid GradeRequestDto updatedGradeDto) {
        Grade updatedGrade = updatedGradeDto.toGrade();

        updateGradeUseCase.execute(gradeId, updatedGrade);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{gradeId}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long gradeId) {
        deleteGradeUseCase.execute(gradeId);

        return ResponseEntity.noContent().build();
    }
}
