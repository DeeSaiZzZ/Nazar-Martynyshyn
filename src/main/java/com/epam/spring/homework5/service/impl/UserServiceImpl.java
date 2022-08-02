package com.epam.spring.homework5.service.impl;

import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.dto.UserDto;
import com.epam.spring.homework5.mapper.OrderMapper;
import com.epam.spring.homework5.mapper.UserMapper;
import com.epam.spring.homework5.model.Order;
import com.epam.spring.homework5.model.User;
import com.epam.spring.homework5.model.enums.Role;
import com.epam.spring.homework5.model.enums.Status;
import com.epam.spring.homework5.model.exeptions.EntityAlreadyExists;
import com.epam.spring.homework5.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework5.model.exeptions.IllegalStateException;
import com.epam.spring.homework5.repository.UserRepository;
import com.epam.spring.homework5.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id-%d not found";

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        log.info("Start create favour");
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EntityAlreadyExists(String.format("User with email %s already exists", userDto.getEmail()));
        }
        User user = userMapper.mapUserDtoToUser(userDto);

        log.trace("New entity - {}", user);
        user.setRole(Role.DEFAULT);
        user = userRepository.save(user);

        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User user = userMapper.mapUserDtoToUser(userDto);
        User persistUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));

        persistUser.update(user);
        userRepository.save(persistUser);

        return userMapper.mapUserToUserDto(persistUser);
    }

    @Override
    public List<UserDto> getAllUser() {
        log.info("Get all user start");
        return userRepository.findAll().stream()
                .map(userMapper::mapUserToUserDto)
                .peek(userDto -> userDto.setPassword(null))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(int id) {
        log.info("Start get user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));

        log.info("Finder user - {}", user);
        UserDto userDto = userMapper.mapUserToUserDto(user);
        userDto.setPassword(null);

        return userDto;
    }

    @Override
    public List<OrderDto> getUserOrder(int id) {
        log.info("Get users order id {}", id);
        User persistUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));

        return persistUser.getUsersOrder().stream()
                .map(orderMapper::orderToOrderDto)
                .peek(orderDto -> {
                    orderDto.getOrderUser().setPassword(null);
                    orderDto.getOrderMaster().setPassword(null);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void payForOrder(int userId, int orderId) {
        log.info("Start pay process");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        log.info("User {} want to pay order with id {}", orderId, user);
        Order order = user.getUsersOrder().stream()
                .filter(o -> o.getId() == orderId)
                .peek(o -> {
                    if (!o.getOrderStatus().equals(Status.AWAITING_PAYMENT)) {
                        throw new IllegalStateException("Order with id-% isn't ready to payment");
                    }
                })
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("User dont have got an order with id-%d", orderId)));

        order.setOrderStatus(Status.PAID);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        log.info("Delete user by id {}", id);
        userRepository.deleteById(id);
    }
}
