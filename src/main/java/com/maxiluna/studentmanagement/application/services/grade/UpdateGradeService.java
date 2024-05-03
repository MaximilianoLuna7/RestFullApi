package com.maxiluna.studentmanagement.application.services.grade;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.GradeNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Grade;
import com.maxiluna.studentmanagement.domain.usecases.grade.UpdateGradeUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.GradeJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateGradeService implements UpdateGradeUseCase {
    @Autowired
    private JpaGradeRepository gradeRepository;

    @Override
    @Transactional
    public void execute(Long gradeId, Grade updatedGrade) {
        if (gradeId <= 0) {
            throw new IllegalArgumentException("Invalid grade ID: " + gradeId);
        }

        try {
            GradeJpa gradeJpa = gradeRepository.findById(gradeId)
                    .orElseThrow(() -> new GradeNotFoundException("Grade not found with ID: " + gradeId));

            gradeJpa.setDescription(updatedGrade.getDescription());
            gradeJpa.setScore(updatedGrade.getScore());
            gradeRepository.save(gradeJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error updating grade: " + ex.getMessage());
        }
    }
}
