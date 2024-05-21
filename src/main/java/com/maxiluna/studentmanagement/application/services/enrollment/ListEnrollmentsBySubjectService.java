package com.maxiluna.studentmanagement.application.services.enrollment;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.Enrollment;
import com.maxiluna.studentmanagement.domain.usecases.enrollment.ListEnrollmentsBySubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.EnrollmentJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListEnrollmentsBySubjectService implements ListEnrollmentsBySubjectUseCase {
    @Autowired
    private JpaEnrollmentRepository enrollmentRepository;
    @Override
    public List<Enrollment> execute(Long subjectId) {
        if (subjectId <= 0) {
            throw new IllegalArgumentException("Invalid subject ID: " + subjectId);
        }

        try {
            List<EnrollmentJpa> enrollmentJpaList = enrollmentRepository.findBySubjectId(subjectId);

            return enrollmentJpaList.stream()
                    .map(EnrollmentJpa::toEnrollment)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error listing enrollments: " + ex.getMessage());
        }
    }
}
