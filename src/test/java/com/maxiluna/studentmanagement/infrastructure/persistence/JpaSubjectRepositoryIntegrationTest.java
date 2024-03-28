package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaSubjectRepositoryIntegrationTest {

    @Autowired
    private JpaSubjectRepository jpaSubjectRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    @DisplayName("Save subject - Successful")
    public void saveSubject_Successful() {
        // Arrange
        SubjectJpa subject = createSubjectJpa();

        // Act
        SubjectJpa savedSubject = jpaSubjectRepository.save(subject);

        // Assert
        assertThat(savedSubject.getId()).isNotNull();
        assertThat(savedSubject.getName()).isEqualTo(subject.getName());
        System.out.println(subject);
    }

    @Test
    @DisplayName("Find subject by ID - Successful")
    public void findSubjectById_Successful() {
        // Arrange
        SubjectJpa subject = createSubjectJpa();
        SubjectJpa savedSubject = jpaSubjectRepository.save(subject);

        // Act
        Optional<SubjectJpa> foundSubjectOptional = jpaSubjectRepository.findById(savedSubject.getId());

        // Assert
        assertThat(foundSubjectOptional).isPresent();
        assertThat(foundSubjectOptional.get().getId()).isEqualTo(savedSubject.getId());
    }

    @Test
    @DisplayName("Find subject by ID - Not found")
    public void findSubjectById_NotFound() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<SubjectJpa> foundSubjectOptional = jpaSubjectRepository.findById(nonExistentId);

        // Assert
        assertThat(foundSubjectOptional).isEmpty();
    }

    @Test
    @DisplayName("Find all subjects - Successful")
    public void findAllSubjects_Successful() {
        // Arrange
        SubjectJpa subject1 = createSubjectJpa();
        SubjectJpa subject2 = SubjectJpa.builder()
                .name("Algebra")
                .academicYear(2020)
                .build();

        jpaSubjectRepository.save(subject1);
        jpaSubjectRepository.save(subject2);

        // Act
        List<SubjectJpa> foundSubjects = jpaSubjectRepository.findAll();

        // Assert
        assertThat(foundSubjects)
                .isNotNull()
                .hasSize(2);
        assertThat(foundSubjects).contains(subject1, subject2);
    }

    @Test
    @DisplayName("Delete subject - Successful")
    public void deleteSubject_Successful() {
        // Arrange
        SubjectJpa subject = createSubjectJpa();
        SubjectJpa savedSubject = jpaSubjectRepository.save(subject);

        // Act
        jpaSubjectRepository.delete(savedSubject);

        // Assert
        Optional<SubjectJpa> deletedSubjectOptional = jpaSubjectRepository.findById(savedSubject.getId());
        assertThat(deletedSubjectOptional).isEmpty();
    }

    private SubjectJpa createSubjectJpa() {
        return SubjectJpa.builder()
                .name("Mathematics")
                .academicYear(2022)
                .build();
    }
}
