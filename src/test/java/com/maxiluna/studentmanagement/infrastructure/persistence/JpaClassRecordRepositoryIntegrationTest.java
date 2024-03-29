package com.maxiluna.studentmanagement.infrastructure.persistence;

import com.maxiluna.studentmanagement.infrastructure.entities.ClassRecordJpa;
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
public class JpaClassRecordRepositoryIntegrationTest {
    @Autowired
    private JpaClassRecordRepository classRecordRepository;

    @Autowired
    private JpaSubjectRepository subjectRepository;

    @Test
    @DisplayName("Save classRecord - Successful")
    public void saveClassRecord_Successful() {
        // Arrange
        ClassRecordJpa classRecord = createClassRecordJpa();

        // Act
        ClassRecordJpa savedClassRecord = classRecordRepository.save(classRecord);

        // Assert
        assertThat(savedClassRecord.getId()).isNotNull();
    }

    @Test
    @DisplayName("Find classRecord by id - Successful")
    public void findClassRecordById_Successful() {
        // Arrange
        ClassRecordJpa classRecord = createClassRecordJpa();
        ClassRecordJpa savedClassRecord = classRecordRepository.save(classRecord);

        // Act
        Optional<ClassRecordJpa> foundClassRecordOptional = classRecordRepository.findById(savedClassRecord.getId());

        // Assert
        assertThat(foundClassRecordOptional).isPresent();
        assertThat(foundClassRecordOptional.get().getId()).isEqualTo(savedClassRecord.getId());
    }

    @Test
    @DisplayName("Find classRecord by id - Not found")
    public void findClassRecordById_NotFound() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<ClassRecordJpa> foundClassRecordOptional = classRecordRepository.findById(nonExistentId);

        // Assert
        assertThat(foundClassRecordOptional).isEmpty();
    }

    @Test
    @DisplayName("Find all classRecords - Successful")
    public void findAllClassRecords_Successful() {
        // Arrange
        ClassRecordJpa classRecord1 = createClassRecordJpa();
        ClassRecordJpa classRecord2 = ClassRecordJpa.builder()
                .topic("Integral")
                .activities("Area calculation")
                .date(LocalDate.now())
                .subject(classRecord1.getSubject())
                .build();

        classRecordRepository.save(classRecord1);
        classRecordRepository.save(classRecord2);

        // Act
        List<ClassRecordJpa> foundClassRecords = classRecordRepository.findAll();

        // Assert
        assertThat(foundClassRecords)
                .isNotNull()
                .hasSize(2);
        assertThat(foundClassRecords).contains(classRecord1, classRecord2);
    }

    @Test
    @DisplayName("Delete classRecord - Successful")
    public void deleteClassRecord_Successful() {
        // Arrange
        ClassRecordJpa classRecord = createClassRecordJpa();
        ClassRecordJpa savedClassRecord = classRecordRepository.save(classRecord);

        // Act
        classRecordRepository.delete(savedClassRecord);

        // Assert
        Optional<ClassRecordJpa> deletedClassRecordOptional = classRecordRepository.findById(savedClassRecord.getId());
        assertThat(deletedClassRecordOptional).isEmpty();
    }

    @Test
    @DisplayName("Find classRecords by subject id - Successful")
    public void findClassRecordsBySubjectId_Successful() {
        // Arrange
        ClassRecordJpa classRecord1 = createClassRecordJpa();
        ClassRecordJpa classRecord2 = ClassRecordJpa.builder()
                .topic("Integral")
                .activities("Area calculation")
                .date(LocalDate.now())
                .subject(classRecord1.getSubject())
                .build();

        Long subjectId = classRecord1.getSubject().getId();

        classRecordRepository.save(classRecord1);
        classRecordRepository.save(classRecord2);

        // Act
        List<ClassRecordJpa> foundClassRecords = classRecordRepository.findBySubjectId(subjectId);

        // Assert
        assertThat(foundClassRecords)
                .isNotNull()
                .hasSize(2);
        assertThat(foundClassRecords).contains(classRecord1, classRecord2);
    }

    private ClassRecordJpa createClassRecordJpa() {
        SubjectJpa subjectJpa = SubjectJpa.builder()
                .name("Mathematics")
                .academicYear(2022)
                .build();
        SubjectJpa savedSubject = subjectRepository.save(subjectJpa);

        return ClassRecordJpa.builder()
                .topic("Derivatives")
                .activities("Problem resolution")
                .date(LocalDate.now())
                .subject(savedSubject)
                .build();
    }
}
