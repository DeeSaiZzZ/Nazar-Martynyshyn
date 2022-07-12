package com.epam.spring.homework4.repository;

import com.epam.spring.homework4.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUser();

    User updateUser(int id, User user);

    User getUserById(int id);

    User createUser(User user);

    void deleteUser(int id);
}
