package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.EnrollmentJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.StudentJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaEnrollmentRepositoryIntegrationTest {
    @Autowired
    private JpaEnrollmentRepository enrollmentRepository;

    @Autowired
    private JpaStudentRepository studentRepository;

    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Test
    @DisplayName("Save enrollment - Successful")
    public void saveEnrollment_Successful() {
        // Arrange
        EnrollmentJpa enrollment = createEnrollmentJpa();

        // Act
        EnrollmentJpa savedEnrollment = enrollmentRepository.save(enrollment);

        // Assert
        assertThat(savedEnrollment.getId()).isNotNull();
    }

    @Test
    @DisplayName("Find enrollment by id - Successful")
    public void findEnrollmentById_Successful() {
        // Arrange
        EnrollmentJpa enrollment = createEnrollmentJpa();
        EnrollmentJpa savedEnrollment = enrollmentRepository.save(enrollment);

        // Act
        Optional<EnrollmentJpa> foundEnrollmentOptional = enrollmentRepository.findById(savedEnrollment.getId());

        // Assert
        assertThat(foundEnrollmentOptional).isPresent();
        assertThat(foundEnrollmentOptional.get().getId()).isEqualTo(savedEnrollment.getId());
    }

    @Test
    @DisplayName("Find enrollment by id - Not found")
    public void findEnrollmentById_NotFound() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<EnrollmentJpa> foundEnrollmentOptional = enrollmentRepository.findById(nonExistentId);

        // Assert
        assertThat(foundEnrollmentOptional).isEmpty();
    }

    @Test
    @DisplayName("Find all enrollments - Successful")
    public void findAllEnrollments_Successful() {
        // Arrange
        EnrollmentJpa enrollment1 = createEnrollmentJpa();
        EnrollmentJpa enrollment2 = EnrollmentJpa.builder()
                .student(enrollment1.getStudent())
                .subject(enrollment1.getSubject())
                .studentStatus("UNSUCCESSFUL")
                .build();

        enrollmentRepository.save(enrollment1);
        enrollmentRepository.save(enrollment2);

        // Act
        List<EnrollmentJpa> foundEnrollments = enrollmentRepository.findAll();

        // Assert
        assertThat(foundEnrollments)
                .isNotNull()
                .hasSize(2);
        assertThat(foundEnrollments).contains(enrollment1, enrollment2);
    }

    @Test
    @DisplayName("Delete enrollment - Successful")
    public void deleteEnrollment_Successful() {
        // Arrange
        EnrollmentJpa enrollment = createEnrollmentJpa();
        EnrollmentJpa savedEnrollment = enrollmentRepository.save(enrollment);

        // Act
        enrollmentRepository.delete(savedEnrollment);

        // Assert
        Optional<EnrollmentJpa> deletedEnrollmentOptional = enrollmentRepository.findById(savedEnrollment.getId());
        assertThat(deletedEnrollmentOptional).isEmpty();
    }

    @Test
    @DisplayName("Find enrollments by student id - Successful")
    public void findEnrollmentsByStudentId_Successful() {
        // Arrange
        EnrollmentJpa enrollment1 = createEnrollmentJpa();
        EnrollmentJpa enrollment2 = EnrollmentJpa.builder()
                .student(enrollment1.getStudent())
                .subject(enrollment1.getSubject())
                .studentStatus("UNSUCCESSFUL")
                .build();

        Long studentId = enrollment1.getStudent().getId();

        enrollmentRepository.save(enrollment1);
        enrollmentRepository.save(enrollment2);

        // Act
        List<EnrollmentJpa> foundEnrollments = enrollmentRepository.findByStudentId(studentId);

        // Assert
        assertThat(foundEnrollments)
                .isNotNull()
                .hasSize(2);
        assertThat(foundEnrollments).contains(enrollment1, enrollment2);
    }

    @Test
    @DisplayName("Find enrollments by subject id - Successful")
    public void findEnrollmentsBySubjectId_Successful() {
        // Arrange
        EnrollmentJpa enrollment1 = createEnrollmentJpa();
        EnrollmentJpa enrollment2 = EnrollmentJpa.builder()
                .student(enrollment1.getStudent())
                .subject(enrollment1.getSubject())
                .studentStatus("UNSUCCESSFUL")
                .build();

        Long subjectId = enrollment1.getSubject().getId();

        enrollmentRepository.save(enrollment1);
        enrollmentRepository.save(enrollment2);

        // Act
        List<EnrollmentJpa> foundEnrollments = enrollmentRepository.findBySubjectId(subjectId);

        // Assert
        assertThat(foundEnrollments)
                .isNotNull()
                .hasSize(2);
        assertThat(foundEnrollments).contains(enrollment1, enrollment2);
    }

    private EnrollmentJpa createEnrollmentJpa() {
        SubjectJpa subject = SubjectJpa.builder()
                .name("Mathematics")
                .academicYear(2022)
                .build();
        SubjectJpa savedSubject = subjectRepository.save(subject);

        StudentJpa student = StudentJpa.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .birthDate(LocalDate.of(2000, 2, 2))
                .dni("12345678")
                .city("Any City")
                .admissionYear(2022)
                .build();
        StudentJpa savedStudent = studentRepository.save(student);

        return EnrollmentJpa.builder()
                .student(savedStudent)
                .subject(savedSubject)
                .studentStatus("APPROVED")
                .build();
    }
}
