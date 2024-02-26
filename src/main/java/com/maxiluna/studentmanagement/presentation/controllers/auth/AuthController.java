package com.maxiluna.studentmanagement.presentation.controllers.auth;

import com.maxiluna.studentmanagement.application.services.auth.AuthService;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.presentation.dtos.AuthResponse;
import com.maxiluna.studentmanagement.presentation.dtos.LoginRequest;
import com.maxiluna.studentmanagement.presentation.dtos.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserDto userToRegister) {
        User user = userToRegister.toUser();
        return ResponseEntity.ok(authService.register(user));
    }
}
