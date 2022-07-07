package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.OrderDtoWithInfo;
import com.epam.spring.homework3.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    void deleteUser(int id);

    List<UserDto> getAllUser();

    List<OrderDtoWithInfo> getUserOrder(int id);
}
