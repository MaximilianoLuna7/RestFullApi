package com.maxiluna.studentmanagement.application.services.auth;

import com.maxiluna.studentmanagement.domain.exceptions.EmailAlreadyExistsException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.usecases.user.RegisterUserUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Override
    @Transactional
    public User register(User userToRegister) {
        if (jpaUserRepository.existsByEmail(userToRegister.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + userToRegister.getEmail());
        }

        UserJpa userJpa = UserJpa.fromUser(userToRegister);
        UserJpa savedUserJpa = jpaUserRepository.save(userJpa);
        return savedUserJpa.toUser();
    }
}
