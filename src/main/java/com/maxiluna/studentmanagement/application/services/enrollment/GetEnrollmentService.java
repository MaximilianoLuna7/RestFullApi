package com.maxiluna.studentmanagement.application.services.enrollment;

import com.maxiluna.studentmanagement.domain.exceptions.EnrollmentNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Enrollment;
import com.maxiluna.studentmanagement.domain.usecases.enrollment.GetEnrollmentUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.EnrollmentJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetEnrollmentService implements GetEnrollmentUseCase {
    @Autowired
    private JpaEnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment execute(Long studentId, Long subjectId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID: " + studentId);
        }
        if (subjectId <= 0) {
            throw new IllegalArgumentException("Invalid subject ID: " + subjectId);
        }

        EnrollmentJpa enrollmentJpa = enrollmentRepository.findByStudentIdAndSubjectId(studentId, subjectId)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found"));

        return enrollmentJpa.toEnrollment();
    }
}
