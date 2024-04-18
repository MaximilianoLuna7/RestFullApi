package com.maxiluna.studentmanagement.application.services.student;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.StudentNotFoundException;
import com.maxiluna.studentmanagement.domain.usecases.student.DeleteStudentUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteStudentService implements DeleteStudentUseCase {
    @Autowired
    private JpaStudentRepository studentRepository;

    @Override
    @Transactional
    public void execute(Long studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID: " + studentId);
        }

        try {
            StudentJpa studentJpa = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));

            studentRepository.delete(studentJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error deleting student information: " + studentId);
        }
    }
}
