package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
import com.maxiluna.studentmanagement.domain.models.Subject;
import com.maxiluna.studentmanagement.infrastructure.entities.SubjectJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GetSubjectServiceTest {
    @Mock
    private JpaSubjectRepository subjectRepository;

    @InjectMocks
    private GetSubjectService getSubjectService;

    @Test
    @DisplayName("Get subject - Successful")
    public void getSubject_Successful() {
        // Arrange
        SubjectJpa subjectJpa = createSubjectJpa();
        Long subjectId = subjectJpa.getId();

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subjectJpa));

        // Act
        Subject subject = getSubjectService.getSubject(subjectId);

        // Assert
        assertThat(subject).isNotNull();
        assertThat(subject.getId()).isEqualTo(subjectId);

        // Verify
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    @DisplayName("Get subject with non existent subject id - Throws exception")
    public void getSubjectWthNonExistentId_ThrowsException() {
        // Arrange
        Long nonExistentId = 999L;

        when(subjectRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> getSubjectService.getSubject(nonExistentId))
                .isInstanceOf(SubjectNotFoundException.class)
                .hasMessageContaining("Subject not found with ID: " + nonExistentId);

        // Verify
        verify(subjectRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Get subject with invalid course id - Throws exception")
    public void getSubjectWthInvalidId_ThrowsException() {
        // Arrange
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> getSubjectService.getSubject(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid subject ID: " + invalidId);

        // Verify
        verifyNoInteractions(subjectRepository);
    }

    private SubjectJpa createSubjectJpa() {
        return SubjectJpa.builder()
                .id(1L)
                .name("Algebra")
                .academicYear(2022)
                .build();
    }
}