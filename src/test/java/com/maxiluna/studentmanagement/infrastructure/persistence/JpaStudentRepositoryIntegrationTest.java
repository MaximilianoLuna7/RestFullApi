package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaStudentRepositoryIntegrationTest {
    @Autowired
    private JpaStudentRepository studentRepository;

    @Test
    @DisplayName("Save student - Successful")
    public void saveStudent_Successful() {
        // Arrange
        StudentJpa student = createStudentJpa();

        // Act
        StudentJpa savedStudent = studentRepository.save(student);

        // Assert
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getFirstName()).isEqualTo(student.getFirstName());
    }

    @Test
    @DisplayName("Find student by id - Successful")
    public void findStudentById_Successful() {
        // Arrange
        StudentJpa student = createStudentJpa();
        StudentJpa savedStudent = studentRepository.save(student);

        // Act
        Optional<StudentJpa> foundStudentOptional = studentRepository.findById(savedStudent.getId());

        // Assert
        assertThat(foundStudentOptional).isPresent();
        assertThat(foundStudentOptional.get().getId()).isEqualTo(savedStudent.getId());
    }

    @Test
    @DisplayName("Find student by id - Not found")
    public void findStudentById_NotFound() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<StudentJpa> foundStudentOptional = studentRepository.findById(nonExistentId);

        // Assert
        assertThat(foundStudentOptional).isEmpty();
    }

    @Test
    @DisplayName("Find all students - Successful")
    public void updateStudent_Successful() {
        // Arrange
        StudentJpa student1 = createStudentJpa();
        StudentJpa student2 = StudentJpa.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .email("bob.johnson@example.com")
                .birthDate(LocalDate.of(2000,3,3))
                .dni("98765432")
                .city("Any City")
                .admissionYear(2022)
                .build();

        studentRepository.save(student1);
        studentRepository.save(student2);

        // Act
        List<StudentJpa> foundSubjects = studentRepository.findAll();

        // Assert
        assertThat(foundSubjects)
                .isNotNull()
                .hasSize(2);
        assertThat(foundSubjects).contains(student1, student2);
    }

    @Test
    @DisplayName("Delete student - Successful")
    public void deleteStudent_Successful() {
        // Arrange
        StudentJpa student = createStudentJpa();
        StudentJpa savedStudent = studentRepository.save(student);

        // Act
        studentRepository.delete(savedStudent);

        // Assert
        Optional<StudentJpa> deletedStudentOptional = studentRepository.findById(savedStudent.getId());
        assertThat(deletedStudentOptional).isEmpty();
    }

    private StudentJpa createStudentJpa() {
        return StudentJpa.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .birthDate(LocalDate.of(2000, 2, 2))
                .dni("12345678")
                .city("Any City")
                .admissionYear(2022)
                .build();
    }
}