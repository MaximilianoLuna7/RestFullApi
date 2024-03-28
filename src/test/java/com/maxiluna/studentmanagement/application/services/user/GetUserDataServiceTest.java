package com.maxiluna.studentmanagement.application.services.user;

import com.maxiluna.studentmanagement.domain.exceptions.UserNotFoundException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaSubjectRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserDataServiceTest {

    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private GetUserDataService getUserDataService;

    @Test
    @DisplayName("Get user data - Successful")
    public void getUserData_Successful() {
        // Arrange
        UserJpa userJpa = createUserJpa();
        Long userId = userJpa.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userJpa));

        // Act
        User user = getUserDataService.getUserData(userId);

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getEmail()).isEqualTo(userJpa.getEmail());

        // Verify
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Get user data with non existent user id - Throws exception")
    public void getUserDataWithNonExistentUserId_ThrowsException() {
        // Arrange
        Long nonExistentId = 999L;
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> getUserDataService.getUserData(nonExistentId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with ID: " + nonExistentId);

        // Verify
        verify(userRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Get user data with invalid user id - Throws IllegalArgumentException")
    public void getUserDataWithInvalidUserId_ThrowsIllegalArgumentException() {
        // Arrange
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> getUserDataService.getUserData(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid user ID");

        // Verify
        verifyNoInteractions(userRepository);
    }

    private UserJpa createUserJpa () {
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
