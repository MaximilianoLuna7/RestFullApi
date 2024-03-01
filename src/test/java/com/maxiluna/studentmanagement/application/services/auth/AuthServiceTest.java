package com.maxiluna.studentmanagement.application.services.auth;

import com.maxiluna.studentmanagement.application.services.jwt.JwtService;
import com.maxiluna.studentmanagement.domain.exceptions.EmailAlreadyExistsException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.models.UserRole;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import com.maxiluna.studentmanagement.presentation.dtos.AuthResponse;
import com.maxiluna.studentmanagement.presentation.dtos.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JpaUserRepository jpaUserRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;
    private final User user = User.builder()
            .id(1L)
            .email("john.doe@example.com")
            .password("password")
            .firstName("John")
            .lastName("Doe")
            .birthDate(LocalDate.of(1995,5,5))
            .role(UserRole.ADMIN)
            .subjects(new HashSet<>())
            .build();
    private final String encodedPassword = "encoded.password";
    private final String token = "mockedToken";
    private final LocalDateTime expirationToken = LocalDateTime.now().plusHours(1);

    @Test
    @DisplayName("Login user - Successful")
    void login_Successful() {
        // Arrange
        LoginRequest request = new LoginRequest(user.getEmail(), user.getPassword());
        UserJpa userJpa = UserJpa.fromUser(user);

        when(jpaUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(userJpa));
        when(jwtService.getToken(any(UserDetails.class))).thenReturn(token);
        when(jwtService.getExpiration(token)).thenReturn(expirationToken);

        // Act
        AuthResponse response = authService.login(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(token);
        assertThat(response.getUserStatus()).isEqualTo("authenticated");
        assertThat(response.getExpirationToken()).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Login successful");

        // Verify
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jpaUserRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, times(1)).getToken(any(UserDetails.class));
        verify(jwtService, times(1)).getExpiration(token);
    }

    @Test
    @DisplayName("Login user with non existent email - Throws exception")
    void loginWithNonExistentEmail_ThrowsException() {
        // Arrange
        LoginRequest request = new LoginRequest(user.getEmail(), user.getPassword());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid email/password"));

        // Act & Assert
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Invalid email/password");

        // Verify
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoMoreInteractions(jpaUserRepository, jwtService);
    }

    @Test
    @DisplayName("Register user - Successful")
    void register_Successful() {
        // Arrange
        when(jpaUserRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);
        when(jwtService.getToken(any(UserDetails.class))).thenReturn(token);
        when(jwtService.getExpiration(token)).thenReturn(expirationToken);

        // Act
        AuthResponse response = authService.register(user);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(token);
        assertThat(response.getUserStatus()).isEqualTo("registered");
        assertThat(response.getExpirationToken()).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Successful registration");

        // Verify
        verify(jpaUserRepository, times(1)).existsByEmail(user.getEmail());
        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(jpaUserRepository, times(1)).save(any(UserJpa.class));
        verify(jwtService, times(1)).getToken(any(UserDetails.class));
        verify(jwtService, times(1)).getExpiration(token);
    }

    @Test
    @DisplayName("Login user with non existent email - Throws exception")
    void registerWithExistentEmail_ThrowsException() {
        // Arrange
        when(jpaUserRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> authService.register(user))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("Email already exists: " + user.getEmail());

        // Verify
        verify(jpaUserRepository, times(1)).existsByEmail(user.getEmail());
        verifyNoMoreInteractions(jpaUserRepository, jwtService, passwordEncoder);
    }
}
