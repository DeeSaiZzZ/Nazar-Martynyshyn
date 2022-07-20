package com.epam.spring.homework4.service.impl;

import com.epam.spring.homework4.dto.OrderDto;
import com.epam.spring.homework4.dto.UserDto;
import com.epam.spring.homework4.mapper.OrderMapper;
import com.epam.spring.homework4.mapper.UserMapper;
import com.epam.spring.homework4.model.User;
import com.epam.spring.homework4.model.enums.Role;
import com.epam.spring.homework4.model.exeptions.EntityAlreadyExists;
import com.epam.spring.homework4.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework4.repository.OrderRepository;
import com.epam.spring.homework4.repository.UserRepository;
import com.epam.spring.homework4.service.UserService;
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
    private final OrderRepository orderRepository;
    private final UserMapper mapper;
    private final OrderMapper orderMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Start create favour");
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EntityAlreadyExists(String.format("Favor with name %s already exists", userDto.getEmail()));
        }
        User user = mapper.mapUserDtoToUser(userDto);

        log.trace("New entity - {}", user);
        user.setRole(Role.DEFAULT);
        user = userRepository.save(user);

        return mapper.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User user = mapper.mapUserDtoToUser(userDto);
        User persistUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id-%d not found", id)));

        persistUser.update(user);
        userRepository.save(persistUser);

        return mapper.mapUserToUserDto(persistUser);
    }

    @Override
    public List<UserDto> getAllUser() {
        log.info("Get all user start");
        return userRepository.findAll().stream()
                .map(mapper::mapUserToUserDto)
                .peek(userDto -> userDto.setPassword(null))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(int id) {
        log.info("Start get user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id-%d not found", id)));

        log.info("Finder user - {}", user);
        UserDto userDto = mapper.mapUserToUserDto(user);
        userDto.setPassword(null);

        return userDto;
    }

    @Override
    public List<OrderDto> getUserOrder(int id) {
        log.info("Get users order id {}", id);
        return orderRepository.findAllOrderByUserId(id).stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int id) {
        log.info("Delete user by id {}", id);
        userRepository.deleteById(id);
    }
}
