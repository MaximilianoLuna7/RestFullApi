package com.maxiluna.studentmanagement.domain.usecases.user;

import com.maxiluna.studentmanagement.domain.models.User;

public interface GetUserDataUseCase {
    User getUserData(Long userId);
}
