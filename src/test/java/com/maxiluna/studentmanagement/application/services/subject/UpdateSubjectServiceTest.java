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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSubjectServiceTest {
    @Mock
    private JpaSubjectRepository subjectRepository;

    @InjectMocks
    private UpdateSubjectService updateSubjectService;

    @Test
    @DisplayName("Update subject - Successful")
    public void updateSubject_Successful() {
        // Arrange
        Subject updatedSubject = createUpdatedSubject();
        SubjectJpa subjectJpa = createSubjectJpa();
        Long subjectId = subjectJpa.getId();

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subjectJpa));

        // Act
        updateSubjectService.execute(subjectId, updatedSubject);

        // Verify
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subjectRepository, times(1)).save(any(SubjectJpa.class));
    }

    @Test
    @DisplayName("Update subject with invalid id - Throws Exception")
    public void updateSubjectWithInvalidId_ThrowsException() {
        // Arrange
        Subject updatedSubject = createUpdatedSubject();
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> updateSubjectService.execute(invalidId, updatedSubject))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid subject ID: " + invalidId);

        // Verify
        verifyNoInteractions(subjectRepository);
    }

    @Test
    @DisplayName("Update non existent subject - Throws Exception")
    public void updateNonExistentSubject_ThrowsException() {
        // Arrange
        Subject updatedSubject = createUpdatedSubject();
        Long nonExistentId = 999L;

        when(subjectRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateSubjectService.execute(nonExistentId, updatedSubject))
                .isInstanceOf(SubjectNotFoundException.class)
                .hasMessageContaining("Subject not found with ID: " + nonExistentId);

        // Verify
        verify(subjectRepository, times(1)).findById(nonExistentId);
        verifyNoMoreInteractions(subjectRepository);
    }

    private SubjectJpa createSubjectJpa() {
        return SubjectJpa.builder()
                .id(1L)
                .name("Algebra")
                .academicYear(2022)
                .build();
    }

    private Subject createUpdatedSubject() {
        return Subject.builder()
                .id(1L)
                .name("Updated")
                .academicYear(2024)
                .build();
    }
}