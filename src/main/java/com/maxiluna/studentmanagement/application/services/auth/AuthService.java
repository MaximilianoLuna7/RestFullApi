package com.maxiluna.studentmanagement.application.services.auth;

import com.maxiluna.studentmanagement.application.services.jwt.JwtService;
import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.exceptions.EmailAlreadyExistsException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import com.maxiluna.studentmanagement.presentation.dtos.AuthResponse;
import com.maxiluna.studentmanagement.presentation.dtos.LoginRequest;
import com.maxiluna.studentmanagement.presentation.dtos.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserJpa userJpa = jpaUserRepository.findByEmail(request.getEmail())
                    .orElseThrow();

            UserDetails user = userJpa.toUser();
            String token = jwtService.getToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .userStatus("authenticated")
                    .expirationToken(jwtService.getExpiration(token))
                    .userInfo(getUserInfo(userJpa))
                    .message("Login successful")
                    .build();
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid email/password");
        }
    }

    public AuthResponse register(User user) {
        try {
            if (jpaUserRepository.existsByEmail(user.getEmail())) {
                throw new EmailAlreadyExistsException("Email already exists: " + user.getEmail());
            }

            UserJpa userJpa = UserJpa.fromUser(user);

            userJpa.setPassword(passwordEncoder.encode(userJpa.getPassword()));
            jpaUserRepository.save(userJpa);

            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                    .token(token)
                    .userStatus("registered")
                    .expirationToken(jwtService.getExpiration(token))
                    .userInfo(getUserInfo(userJpa))
                    .message("Successful registration")
                    .build();
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error while accessing the database");
        }
    }

    public UserInfoDto getUserInfo(UserJpa user) {
        return new UserInfoDto(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}
