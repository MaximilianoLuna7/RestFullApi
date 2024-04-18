package com.maxiluna.studentmanagement.presentation.controllers;

import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.usecases.user.DeleteUserUseCase;
import com.maxiluna.studentmanagement.domain.usecases.user.GetUserDataUseCase;
import com.maxiluna.studentmanagement.domain.usecases.user.ListUsersUseCase;
import com.maxiluna.studentmanagement.domain.usecases.user.UpdateUserDataUseCase;
import com.maxiluna.studentmanagement.presentation.dtos.user.UserResponseDto;
import com.maxiluna.studentmanagement.presentation.dtos.user.UserUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private GetUserDataUseCase getUserDataUseCase;

    @Autowired
    private UpdateUserDataUseCase updateUserDataUseCase;

    @Autowired
    private DeleteUserUseCase deleteUserUseCase;

    @Autowired
    private ListUsersUseCase listUsersUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        User user = getUserDataUseCase.execute(userId);

        return ResponseEntity.ok(UserResponseDto.fromUser(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody @Valid UserUpdateDto updatedUserDto) {
        User updatedUser = updatedUserDto.toUser();
        updateUserDataUseCase.execute(userId, updatedUser);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        deleteUserUseCase.execute(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        List<User> usersList = listUsersUseCase.execute();

        List<UserResponseDto> usersListDto = usersList.stream()
                .map(UserResponseDto::fromUser)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usersListDto);
    }
}
