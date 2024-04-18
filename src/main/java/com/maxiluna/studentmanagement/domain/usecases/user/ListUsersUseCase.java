package com.maxiluna.studentmanagement.domain.usecases.user;

import com.maxiluna.studentmanagement.domain.models.User;

import java.util.List;

public interface ListUsersUseCase {
    List<User> execute();
}
