package com.epam.spring.homework3.repository.impl;

import com.epam.spring.homework3.model.User;
import com.epam.spring.homework3.model.enums.Role;
import com.epam.spring.homework3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final List<User> userList;
    private int idCounter = 1;

    @Override
    public List<User> getAllUser() {
        log.info("Get all user");
        return new ArrayList<>(userList);
    }

    @Override
    public User getUserById(int id) {
        log.info("Get user by id {}", id);
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public User createUser(User user) {
        log.info("Create user");
        log.trace("User id = {}", idCounter);
        user.setId(idCounter++);
        user.setRole(Role.DEFAULT);
        userList.add(user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        log.info("Delete user by id {}", id);
        userList.removeIf(u -> u.getId() == id);
    }
}
