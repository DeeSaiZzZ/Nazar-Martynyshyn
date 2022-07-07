package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.OrderDtoWithInfo;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.mapper.UserMapper;
import com.epam.spring.homework3.model.User;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.service.OrderService;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final UserMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Start create favour");
        User user = mapper.mapUserDtoToUser(userDto);
        log.trace("New entity - {}", user);
        user = userRepository.createUser(user);
        return mapper.mapUserToUserDto(user);
    }

    @Override
    public void deleteUser(int id) {
        log.info("Delete user by id {}", id);
        userRepository.deleteUser(id);
    }

    @Override
    public List<UserDto> getAllUser() {
        log.info("Get all user start");
        return userRepository
                .getAllUser()
                .stream()
                .map(mapper::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDtoWithInfo> getUserOrder(int id) {
        log.info("Get users order id {}", id);
        return orderService.getOrderByUserId(id);
    }
}
