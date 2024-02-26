package com.maxiluna.studentmanagement.domain.usecases.user;

import com.maxiluna.studentmanagement.domain.models.User;

public interface RegisterUserUseCase {
    User register(User userToRegister);
}
