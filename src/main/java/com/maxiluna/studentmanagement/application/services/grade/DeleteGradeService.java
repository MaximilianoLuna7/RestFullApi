package com.maxiluna.studentmanagement.application.services.grade;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.GradeNotFoundException;
import com.maxiluna.studentmanagement.domain.usecases.grade.DeleteGradeUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.GradeJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteGradeService implements DeleteGradeUseCase {
    @Autowired
    private JpaGradeRepository gradeRepository;

    @Override
    @Transactional
    public void execute(Long gradeId) {
        if (gradeId <= 0) {
            throw new IllegalArgumentException("Invalid grade ID: " + gradeId);
        }

        try {
            GradeJpa gradeJpa = gradeRepository.findById(gradeId)
                    .orElseThrow(() -> new GradeNotFoundException("Grade not found with ID: " + gradeId));

            gradeRepository.delete(gradeJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error deleting grade: " + ex.getMessage());
        }
    }
}
