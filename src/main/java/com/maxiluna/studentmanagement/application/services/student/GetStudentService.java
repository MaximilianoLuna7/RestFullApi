package com.maxiluna.studentmanagement.application.services.student;

import com.maxiluna.studentmanagement.domain.exceptions.StudentNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.usecases.student.GetStudentUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetStudentService implements GetStudentUseCase {
    @Autowired
    private JpaStudentRepository studentRepository;

    @Override
    public Student execute(Long studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID: " + studentId);
        }

        StudentJpa studentJpa = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));

        return studentJpa.toStudent();
    }
}
