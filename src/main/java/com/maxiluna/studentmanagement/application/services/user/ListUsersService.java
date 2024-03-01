package com.maxiluna.studentmanagement.application.services.user;

import com.maxiluna.studentmanagement.domain.exceptions.DatabaseErrorException;
import com.maxiluna.studentmanagement.domain.models.User;
import com.maxiluna.studentmanagement.domain.usecases.user.ListUsersUseCase;
import com.maxiluna.studentmanagement.infrastructure.entities.UserJpa;
import com.maxiluna.studentmanagement.infrastructure.persistence.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListUsersService implements ListUsersUseCase {

    @Autowired
    private JpaUserRepository userRepository;

    @Override
    public List<User> listUsers() {
        try {
            List<UserJpa> usersList = userRepository.findAll();

            return usersList.stream()
                    .map(UserJpa::toUser)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseErrorException("Error listing users: " + ex.getMessage());
        }
    }
}
