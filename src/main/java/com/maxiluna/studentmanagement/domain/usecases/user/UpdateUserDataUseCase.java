package com.maxiluna.studentmanagement.domain.usecases.user;

import com.maxiluna.studentmanagement.domain.models.User;

public interface UpdateUserDataUseCase {
    void updateUserData(Long userId, User updatedUser);
}
