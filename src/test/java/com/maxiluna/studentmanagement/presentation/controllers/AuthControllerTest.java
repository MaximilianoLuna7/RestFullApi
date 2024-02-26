package com.maxiluna.studentmanagement.presentation.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maxiluna.studentmanagement.application.services.auth.AuthService;
import com.maxiluna.studentmanagement.config.jwt.JwtAuthenticationFilter;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.EmailAlreadyExistsException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.models.UserRole;
import com.maxiluna.studentmanagement.presentation.dtos.AuthResponse;
import com.maxiluna.studentmanagement.presentation.dtos.LoginRequest;
import com.maxiluna.studentmanagement.presentation.dtos.UserDto;
import com.maxiluna.studentmanagement.presentation.dtos.UserInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Login user - Successful")
    public void loginUser_Successful() throws Exception {
        // Arrange
        UserDto user = createUserDto();
        LoginRequest request = new LoginRequest(user.getEmail(), user.getPassword());
        AuthResponse response = createAuthResponse();

        when(authService.login(request)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(response.getToken()))
                .andExpect(jsonPath("$.message").value(response.getMessage()))
                .andExpect(jsonPath("$.userStatus").value(response.getUserStatus()))
                .andExpect(jsonPath("$.userInfo.id").value(response.getUserInfo().getId()))
                .andExpect(jsonPath("$.userInfo.email").value(response.getUserInfo().getEmail()))
                .andExpect(jsonPath("$.userInfo.userRole").value(response.getUserInfo().getUserRole()));


        // Verify
        verify(authService, times(1)).login(request);
    }

    @Test
    @DisplayName("Login user with invalid credentials - Throws BadCredentialsException")
    public void loginWithInvalidCredentials_ThrowsException() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("invalid@example.com", "invalidpassword");

        when(authService.login(request)).thenThrow(new BadCredentialsException("Invalid email/password"));

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value("Invalid email/password"))
                .andExpect(jsonPath("$.path").value("/auth/login"));

        // Verify
        verify(authService, times(1)).login(request);
    }


    @Test
    @DisplayName("Register user - Successful")
    public void registerUser_Successful() throws Exception {
        // Arrange
        UserDto userDto = createUserDto();
        User userToRegister = userDto.toUser();
        AuthResponse response = createAuthResponse();

        when(authService.register(userToRegister)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(response.getToken()))
                .andExpect(jsonPath("$.message").value(response.getMessage()))
                .andExpect(jsonPath("$.userStatus").value(response.getUserStatus()))
                .andExpect(jsonPath("$.userInfo.id").value(response.getUserInfo().getId()))
                .andExpect(jsonPath("$.userInfo.email").value(response.getUserInfo().getEmail()))
                .andExpect(jsonPath("$.userInfo.userRole").value(response.getUserInfo().getUserRole()));


        // Verify
        verify(authService, times(1)).register(userToRegister);
    }

    @Test
    @DisplayName("Register user with existent email - Throws EmailAlreadyExistsException")
    public void registerWithExistentEmail_ThrowsException() throws Exception {
        // Arrange
        UserDto userDto = createUserDto();
        User user = userDto.toUser();

        when(authService.register(user)).thenThrow(new EmailAlreadyExistsException("Email already exists: " + user.getEmail()));

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value("Email already exists: " + user.getEmail()))
                .andExpect(jsonPath("$.path").value("/auth/register"));

        // Verify
        verify(authService, times(1)).register(user);
    }

    @Test
    @DisplayName("Database error during registration - Throws DatabaseErrorException")
    public void registerWithDatabaseError_ThrowsException() throws Exception {
        // Arrange
        UserDto userDto = createUserDto();
        User user = userDto.toUser();

        when(authService.register(user)).thenThrow(new DatabaseErrorException("Error while accessing the database"));

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.error").value("Database Error"))
                .andExpect(jsonPath("$.message").value("Error while accessing the database"))
                .andExpect(jsonPath("$.path").value("/auth/register"));

        // Verify
        verify(authService, times(1)).register(user);
    }

    @Test
    @DisplayName("Handle Validation Exception - Global Exception Handler")
    public void handleValidationException_GlobalExceptionHandler() throws Exception {
        // Arrange
        UserDto invalidUserDto = createUserDtoWithInvalidFields();
        User user = invalidUserDto.toUser();

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").value(containsString("Email must not be blank")))
                .andExpect(jsonPath("$.message").value(containsString("Password must not be blank")))
                .andExpect(jsonPath("$.message").value(containsString("First name must be between 2 and 100 characters")))
                .andExpect(jsonPath("$.message").value(containsString("Last name must be between 2 and 100 characters")))
                .andExpect(jsonPath("$.message").value(containsString("Birth date must be in the past")))
                .andExpect(jsonPath("$.message").value(containsString("Role must not be null")))
                .andExpect(jsonPath("$.path").value("/auth/register"));

        // Verify
        verify(authService, never()).register(user);
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(obj);
    }

    private UserDto createUserDto() {
        return UserDto.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000, 2, 5))
                .role(UserRole.TEACHER)
                .build();
    }
    private AuthResponse createAuthResponse() {
        UserInfoDto userInfoDto = new UserInfoDto(1L, "test@example.com", "TEACHER");
        return new AuthResponse("token123","Successful registration", "registered", LocalDateTime.now().plusHours(1), userInfoDto);
    }

    private UserDto createUserDtoWithInvalidFields() {
        return UserDto.builder()
                .firstName("A")
                .lastName("A")
                .birthDate(LocalDate.now().plusDays(1))
                .build();
    }
}