package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.mapper.UserMapper;
import com.epam.spring.homework3.model.User;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.service.OrderService;
import com.epam.spring.homework3.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper, @Lazy OrderService orderService) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Start create favour");
        User user = mapper.mapUserDtoToUser(userDto);
        log.trace("New entity - {}", user);
        user = userRepository.createUser(user);
        return mapper.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User user = mapper.mapUserDtoToUser(userDto);
        user = userRepository.updateUser(id, user);
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
        return userRepository.getAllUser().stream()
                .map(mapper::mapUserToUserDto)
                .peek(userDto -> userDto.setPassword(null))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(int id) {
        UserDto userDto = mapper.mapUserToUserDto(userRepository.getUserById(id));
        userDto.setPassword(null);
        return userDto;
    }

    @Override
    public List<OrderDto> getUserOrder(int id) {
        log.info("Get users order id {}", id);
        return orderService.getAllOrder().stream()
                .filter(orderDto -> orderDto.getOrderUser().getId() == id)
                .collect(Collectors.toList());
    }
}
