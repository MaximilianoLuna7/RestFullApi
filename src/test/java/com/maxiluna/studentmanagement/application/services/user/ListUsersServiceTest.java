package com.maxiluna.studentmanagement.application.services.user;

import com.maxiluna.studentmanagement.domain.models.User;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUsersServiceTest {

    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private ListUsersService listUsersService;

    @Test
    @DisplayName("List users - Successful")
    public void listUsers_Successful() {
        // Arrange
        List<UserJpa> userJpaList = createUserJpaList();
        List<User> expectedUsers = userJpaList.stream()
                        .map(UserJpa::toUser)
                        .collect(Collectors.toList());

        when(userRepository.findAll()).thenReturn(userJpaList);

        // Act
        List<User> actualUsers = listUsersService.execute();

        // Assert
        assertThat(actualUsers).isNotNull();
        assertThat(actualUsers)
                .hasSize(expectedUsers.size())
                .containsAll(expectedUsers);

        // Verify
        verify(userRepository, times(1)).findAll();
    }


    private List<UserJpa> createUserJpaList() {
        List<UserJpa> users = new ArrayList<>();
        users.add(createUserJpa(1L, "john.doe@example.com", "password123", "John", "Doe", LocalDate.of(1995, 5, 5), "ADMIN"));
        users.add(createUserJpa(2L, "jane.smith@example.com", "password456", "Jane", "Smith", LocalDate.of(1995, 7, 7), "TEACHER"));
        // Add more users if needed
        return users;
    }

    private UserJpa createUserJpa(Long id, String email, String password, String firstName, String lastName, LocalDate birthDate, String role) {
        return UserJpa.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .role(role)
                .subjects(new ArrayList<>())
                .build();
    }
}
