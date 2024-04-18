package com.maxiluna.studentmanagement.application.services.student;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.usecases.student.GetStudentsBySubjectUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.EnrollmentJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaEnrollmentRepository;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetStudentsBySubjectService implements GetStudentsBySubjectUseCase {
    @Autowired
    private JpaStudentRepository studentRepository;

    @Autowired
    private JpaEnrollmentRepository enrollmentRepository;

    @Override
    public List<Student> execute(Long subjectId) {
        if (subjectId <= 0) {
            throw new IllegalArgumentException("Invalid subject ID: " + subjectId);
        }

        try {
            List<EnrollmentJpa> enrollmentJpaList = enrollmentRepository.findBySubjectId(subjectId);

            List<StudentJpa> studentJpaList = enrollmentJpaList.stream()
                    .map(EnrollmentJpa::getStudent)
                    .toList();

            return studentJpaList.stream()
                    .map(StudentJpa::toStudent)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error listing students: " + ex.getMessage());
        }
    }
}
