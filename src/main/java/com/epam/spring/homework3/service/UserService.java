package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(int id, UserDto userDto);

    void deleteUser(int id);

    List<UserDto> getAllUser();

    UserDto getUser(int id);

    List<OrderDto> getUserOrder(int id);
}
