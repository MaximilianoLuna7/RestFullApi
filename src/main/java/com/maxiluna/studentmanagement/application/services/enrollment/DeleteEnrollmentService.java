package com.maxiluna.studentmanagement.application.services.enrollment;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.EnrollmentNotFoundException;
import com.maxiluna.studentmanagement.domain.usecases.enrollment.DeleteEnrollmentUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.EnrollmentJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteEnrollmentService implements DeleteEnrollmentUseCase {
    @Autowired
    private JpaEnrollmentRepository enrollmentRepository;

    @Override
    @Transactional
    public void execute(Long studentId, Long subjectId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID: " + studentId);
        }
        if (subjectId <= 0) {
            throw new IllegalArgumentException("Invalid subject ID: " + subjectId);
        }

        try {
            EnrollmentJpa enrollmentJpa = enrollmentRepository.findByStudentIdAndSubjectId(studentId, subjectId)
                    .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found"));

            enrollmentRepository.delete(enrollmentJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error deleting enrollment: " + ex.getMessage());
        }
    }
}
