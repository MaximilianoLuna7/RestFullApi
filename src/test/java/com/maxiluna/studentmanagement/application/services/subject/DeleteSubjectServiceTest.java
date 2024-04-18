package com.maxiluna.studentmanagement.application.services.subject;

import com.maxiluna.studentmanagement.domain.exceptions.SubjectNotFoundException;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class DeleteSubjectServiceTest {
    @Mock
    private JpaSubjectRepository subjectRepository;

    @InjectMocks
    private DeleteSubjectService deleteSubjectService;

    @Test
    @DisplayName("Delete subject - Successful")
    public void deleteSubject_Successful() {
        // Arrange
        SubjectJpa subjectJpa = createSubjectJpa();
        Long subjectId = subjectJpa.getId();;

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subjectJpa));

        // Act
        deleteSubjectService.execute(subjectId);

        // Verify
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subjectRepository, times(1)).delete(subjectJpa);
    }

    @Test
    @DisplayName("Delete non existent subject - Throws Exception")
    public void deleteNonExistentSubject_ThrowsException() {
        // Arrange
        Long nonExistentId = 999L;

        when(subjectRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> deleteSubjectService.execute(nonExistentId))
                .isInstanceOf(SubjectNotFoundException.class)
                .hasMessageContaining("Subject not found with ID: " + nonExistentId);

        // Verify
        verify(subjectRepository, times(1)).findById(nonExistentId);
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    @DisplayName("Delete subject with invalid id - Throws Exception")
    public void deleteSubjectWithInvalidId_ThrowsException() {
        // Arrange
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> deleteSubjectService.execute(invalidId))
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