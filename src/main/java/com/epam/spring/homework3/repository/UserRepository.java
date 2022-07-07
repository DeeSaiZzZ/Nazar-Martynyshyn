package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUser();

    User getUserById(int id);

    User createUser(User user);

    void deleteUser(int id);
}
