package com.maxiluna.studentmanagement.application.services.grade;

import com.maxiluna.studentmanagement.domain.exceptions.GradeNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Grade;
import com.maxiluna.studentmanagement.domain.usecases.grade.GetGradeUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.GradeJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetGradeService implements GetGradeUseCase {
    @Autowired
    private JpaGradeRepository gradeRepository;

    @Override
    public Grade execute(Long gradeId) {
        if (gradeId <= 0) {
            throw new IllegalArgumentException("Invalid grade ID: " + gradeId);
        }

        GradeJpa gradeJpa = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFoundException("Grade not found with ID: " + gradeId));

        return gradeJpa.toGrade();
    }
}
