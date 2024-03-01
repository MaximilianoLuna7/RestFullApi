package com.maxiluna.studentmanagement.application.services.user;

import com.maxiluna.studentmanagement.domain.exceptions.UserNotFoundException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.models.UserRole;
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
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserDataServiceTest {

    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private UpdateUserDataService updateUserDataService;

    @Test
    @DisplayName("Update user - Successful")
    public void updateUser_Successful() {
        // Arrange
        User updatedUser = createUser();
        UserJpa userJpa = createUserJpa();
        Long userId = userJpa.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userJpa));

        // Act
        updateUserDataService.updateUserData(userId, updatedUser);

        // Verify
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(UserJpa.class));
    }

    @Test
    @DisplayName("Update user with invalid user id - Throws IllegalArgumentException")
    public void updateUserWithInvalidUserId_ThrowsIllegalArgumentException() {
        // Arrange
        Long invalidId = 0L;
        User updatedUser = createUser();

        // Act & Assert
        assertThatThrownBy(() -> updateUserDataService.updateUserData(invalidId, updatedUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid user ID");
    }

    @Test
    @DisplayName("Update non existent user - Throws exception")
    public void updateNonExistentUser_ThrowsException() {
        // Arrange
        User updatedUser = createUser();
        Long nonExistentUserId = 999L;

        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateUserDataService.updateUserData(nonExistentUserId, updatedUser))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with ID: " + nonExistentUserId);

        // Verify
        verify(userRepository, times(1)).findById(nonExistentUserId);
        verifyNoMoreInteractions(userRepository);
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

    private User createUser() {
        return User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1995,5,5))
                .role(UserRole.ADMIN)
                .subjects(new HashSet<>())
                .build();
    }
}