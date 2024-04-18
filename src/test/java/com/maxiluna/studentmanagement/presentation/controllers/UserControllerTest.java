package com.maxiluna.studentmanagement.presentation.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maxiluna.studentmanagement.config.jwt.JwtAuthenticationFilter;
import com.maxiluna.studentmanagement.domain.exceptions.UserNotFoundException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.models.UserRole;
import com.maxiluna.studentmanagement.domain.usecases.user.DeleteUserUseCase;
import com.maxiluna.studentmanagement.domain.usecases.user.GetUserDataUseCase;
import com.maxiluna.studentmanagement.domain.usecases.user.ListUsersUseCase;
import com.maxiluna.studentmanagement.domain.usecases.user.UpdateUserDataUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.presentation.dtos.user.UserResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.user.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private GetUserDataUseCase getUserDataUseCase;

    @MockBean
    private UpdateUserDataUseCase updateUserDataUseCase;

    @MockBean
    private DeleteUserUseCase deleteUserUseCase;

    @MockBean
    private ListUsersUseCase listUsersUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Get user by ID - Successful")
    public void getUserById_Successful() throws Exception {
        // Arrange
        Long userId = 1L;
        User user = createUser();
        UserResponseDto userResponseDto = UserResponseDto.fromUser(user);

        when(getUserDataUseCase.execute(userId)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userResponseDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userResponseDto.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(userResponseDto.getBirthDate().toString()))
                .andExpect(jsonPath("$.role").value(userResponseDto.getRole().toString()));

        // Verify
        verify(getUserDataUseCase, times(1)).execute(userId);
    }

    @Test
    @DisplayName("Get user by ID - Invalid ID")
    public void getUserById_InvalidId_ErrorResponse() throws Exception {
        // Arrange
        Long invalidUserId = -1L;
        String expectedErrorMessage = "Invalid user ID: " + invalidUserId;

        when(getUserDataUseCase.execute(invalidUserId))
                .thenThrow(new IllegalArgumentException(expectedErrorMessage));

        // Act & Assert
        mockMvc.perform(get("/api/users/{userId}", invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/users/" + invalidUserId));

        // Verify
        verify(getUserDataUseCase, times(1)).execute(invalidUserId);
    }

    @Test
    @DisplayName("Get user by ID - User not found")
    public void getUserById_UserNotFound_ErrorResponse() throws Exception {
        // Arrange
        Long nonExistentUserId = 999L;
        String expectedErrorMessage = "User not found with ID: " + nonExistentUserId;

        when(getUserDataUseCase.execute(nonExistentUserId))
                .thenThrow(new UserNotFoundException(expectedErrorMessage));

        // Act & Assert
        mockMvc.perform(get("/api/users/{userId}", nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/users/" + nonExistentUserId));

        // Verify
        verify(getUserDataUseCase, times(1)).execute(nonExistentUserId);
    }

    @Test
    @DisplayName("Update user - Successful")
    public void updateUser_Successful() throws Exception {
        // Arrange
        Long userId = 1L;
        UserUpdateDto updateDto = createUserUpdateDto();

        // Act & Assert
        mockMvc.perform(put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDto)))
                .andExpect(status().isNoContent());

        // Verify
        verify(updateUserDataUseCase, times(1)).execute(userId, updateDto.toUser());
    }

    @Test
    @DisplayName("Update user with invalid data - Validation Error")
    public void updateUser_InvalidData_ValidationError() throws Exception {
        // Arrange
        Long userId = 1L;
        UserUpdateDto updateDto = new UserUpdateDto();

        // Act & Assert
        mockMvc.perform(put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.path").value("/api/users/" + userId));
    }

    @Test
    @DisplayName("Update non-existent user - Not Found")
    public void updateNonExistentUser_NotFound() throws Exception {
        // Arrange
        Long nonExistentUserId = 999L;
        UserUpdateDto updateDto = createUserUpdateDto();
        String expectedErrorMessage = "User not found with ID: " + nonExistentUserId;

        doThrow(new UserNotFoundException(expectedErrorMessage))
                .when(updateUserDataUseCase).execute(nonExistentUserId, updateDto.toUser());

        // Act & Assert
        mockMvc.perform(put("/api/users/{userId}", nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/users/" + nonExistentUserId));

        // Verify
        verify(updateUserDataUseCase, times(1)).execute(nonExistentUserId, updateDto.toUser());
    }

    @Test
    @DisplayName("Delete user - Successful")
    public void deleteUser_Successful() throws Exception {
        // Arrange
        Long userId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/users/{userId}", userId))
                .andExpect(status().isNoContent());

        // Verify
        verify(deleteUserUseCase, times(1)).execute(userId);
    }

    @Test
    @DisplayName("Delete non existent user - Not Found")
    public void deleteNonExistentUser_NotFound() throws Exception {
        // Arrange
        Long nonExistentUserId = 999L;
        String expectedErrorMessage = "User not found.";

        doThrow(new UserNotFoundException(expectedErrorMessage))
                .when(deleteUserUseCase).execute(nonExistentUserId);

        // Act & Assert
        mockMvc.perform(delete("/api/users/{userId}", nonExistentUserId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.path").value("/api/users/" + nonExistentUserId));
    }

    @Test
    @DisplayName("List users - Successful")
    public void listUsers_Successful() throws Exception {
        // Arrange
        List<UserJpa> userJpaList = createUserJpaList();
        List<User> expectedUsers = userJpaList.stream()
                .map(UserJpa::toUser)
                .collect(Collectors.toList());

        when(listUsersUseCase.execute()).thenReturn(expectedUsers);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedUsers.size())))
                .andExpect(jsonPath("$[0].id").value(expectedUsers.get(0).getId()))
                .andExpect(jsonPath("$[0].email").value(expectedUsers.get(0).getEmail()))
                .andExpect(jsonPath("$[0].firstName").value(expectedUsers.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(expectedUsers.get(0).getLastName()))
                .andExpect(jsonPath("$[0].role").value(expectedUsers.get(0).getRole().toString()))

                .andExpect(jsonPath("$[1].id").value(expectedUsers.get(1).getId()))
                .andExpect(jsonPath("$[1].email").value(expectedUsers.get(1).getEmail()))
                .andExpect(jsonPath("$[1].firstName").value(expectedUsers.get(1).getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(expectedUsers.get(1).getLastName()))
                .andExpect(jsonPath("$[1].role").value(expectedUsers.get(1).getRole().toString()));

        verify(listUsersUseCase, times(1)).execute();
    }

    private List<UserJpa> createUserJpaList() {
        List<UserJpa> users = new ArrayList<>();
        users.add(createUserJpa(1L, "john.doe@example.com", "password123", "John", "Doe", LocalDate.of(1995, 5, 5), "ADMIN"));
        users.add(createUserJpa(2L, "jane.smith@example.com", "password456", "Jane", "Smith", LocalDate.of(1995, 7, 7), "TEACHER"));
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

    private User createUser() {
        return User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1995,5,5))
                .role(UserRole.ADMIN)
                .subjects(new ArrayList<>())
                .build();
    }

    private UserUpdateDto createUserUpdateDto() {
        return UserUpdateDto.builder()
                .firstName("Update")
                .lastName("User")
                .birthDate(LocalDate.of(1993,11,2))
                .build();
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(obj);
    }
}