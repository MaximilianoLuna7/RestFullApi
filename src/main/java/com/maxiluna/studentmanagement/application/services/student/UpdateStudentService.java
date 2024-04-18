package com.maxiluna.studentmanagement.application.services.student;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.StudentNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Student;
import com.maxiluna.studentmanagement.domain.usecases.student.UpdateStudentUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateStudentService implements UpdateStudentUseCase {
    @Autowired
    private JpaStudentRepository studentRepository;

    @Override
    @Transactional
    public void execute(Long studentId, Student updatedStudent) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID: " + studentId);
        }

        try {
            StudentJpa studentJpa = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));

            studentJpa.setFirstName(updatedStudent.getFirstName());
            studentJpa.setLastName(updatedStudent.getLastName());
            studentJpa.setEmail(updatedStudent.getEmail());
            studentJpa.setBirthDate(updatedStudent.getBirthDate());
            studentJpa.setDni(updatedStudent.getDni());
            studentJpa.setCity(updatedStudent.getCity());
            studentJpa.setAdmissionYear(updatedStudent.getAdmissionYear());

            studentRepository.save(studentJpa);
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error updating student: " + ex.getMessage());
        }
    }
}
