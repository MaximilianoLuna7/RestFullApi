package com.maxiluna.studentmanagement.application.services.enrollment;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.StudentNotFoundException;
import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.models.StudentStatus;
import com.maxiluna.studentmanagement.domain.usecases.enrollment.CreateEnrollmentUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.EnrollmentJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaEnrollmentRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CreateEnrollmentService implements CreateEnrollmentUseCase {
    @Autowired
    private JpaEnrollmentRepository enrollmentRepository;

    @Autowired
    private JpaStudentRepository studentRepository;

    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Override
    @Transactional
    public void execute(Long studentId, Long subjectId, StudentStatus status) {
        try {
            StudentJpa studentJpa = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
            SubjectJpa subjectJpa = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new SubjectNotFoundException("Subject not found with ID: " + subjectId));

            EnrollmentJpa enrollmentJpa = EnrollmentJpa.builder()
                    .student(studentJpa)
                    .subject(subjectJpa)
                    .studentStatus(status.toString())
                    .build();

            if (studentJpa.getEnrollments() == null) {
                studentJpa.setEnrollments(new ArrayList<>());
            }
            studentJpa.getEnrollments().add(enrollmentJpa);
            studentRepository.save(studentJpa);

            enrollmentRepository.save(enrollmentJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error creating enrollment: " + ex.getMessage());
        }
    }
}
