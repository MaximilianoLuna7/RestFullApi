package com.maxiluna.studentmanagement.application.services.user;

import com.maxiluna.studentmanagement.domain.exceptions.UserNotFoundException;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserServiceTest {

    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private DeleteUserService deleteUserService;

    @Test
    @DisplayName("Delete user account - Successful")
    public void deleteUserAccount_Successful() {
        // Arrange
        UserJpa userJpa = createUserJpa();
        Long userId = userJpa.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userJpa));

        // Act
        deleteUserService.deleteUserAccount(userId);

        // Verify
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(userJpa);
    }

    @Test
    @DisplayName("Delete non existent user account - Successful")
    public void deleteNonExistentUserAccount_Successful() {
        // Arrange
        Long nonExistentUserId = 999L;

        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> deleteUserService.deleteUserAccount(nonExistentUserId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");

        // Verify
        verify(userRepository, times(1)).findById(nonExistentUserId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Delete user account with invalid user id - Throws IllegalArgumentException")
    public void deleteUserAccountWithInvalidUserId_ThrowsIllegalArgumentException() {
        // Arrange
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> deleteUserService.deleteUserAccount(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid user ID");
    }

    private UserJpa createUserJpa() {
        return UserJpa.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1995,5,5))
                .role("ADMIN")
                .subjects(new ArrayList<>())
                .build();
    }
}