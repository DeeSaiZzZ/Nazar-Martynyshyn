package com.epam.spring.homework5.service;

import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.dto.UserDto;
import com.epam.spring.homework5.mapper.OrderMapperImpl;
import com.epam.spring.homework5.mapper.UserMapperImpl;
import com.epam.spring.homework5.model.User;
import com.epam.spring.homework5.model.enums.Status;
import com.epam.spring.homework5.model.exeptions.EntityAlreadyExists;
import com.epam.spring.homework5.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework5.model.exeptions.IllegalStateException;
import com.epam.spring.homework5.repository.UserRepository;
import com.epam.spring.homework5.service.impl.UserServiceImpl;
import com.epam.spring.homework5.test.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Spy
    UserMapperImpl userMapper;

    @Spy
    OrderMapperImpl orderMapper;

    @Test
    void createUserTest() {
        User testUser = TestUtil.createTestUser();

        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.save(testUser)).thenReturn(testUser);

        UserDto result = userService.createUser(userMapper.mapUserToUserDto(testUser));

        assertThat(result, equalTo(userMapper.mapUserToUserDto(testUser)));
    }

    @Test
    void createUserUserAlreadyExist() {
        User testUser = TestUtil.createTestUser();

        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(true);

        assertThrows(EntityAlreadyExists.class,
                () -> userService.createUser(userMapper.mapUserToUserDto(testUser)));
    }

    @Test
    void updateUser() {
        User testUser = TestUtil.createTestUser();

        UserDto newUserModel = userMapper.mapUserToUserDto(TestUtil.createTestUser());
        newUserModel.setName("NewTestName");

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        UserDto result = userService.updateUser(testUser.getId(), newUserModel);

        assertThat(result, hasProperty("name", equalTo(newUserModel.getName())));
    }

    @Test
    void updateUserUserNotFound() {
        UserDto userDto = new UserDto();
        int testId = 555;

        when(userRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(testId, userDto));
    }

    @Test
    void getAllUserTest() {
        List<User> userTestList = TestUtil.createUserTestList();

        when(userRepository.findAll()).thenReturn(userTestList);

        List<UserDto> result = userService.getAllUser();

        assertThat(userTestList.stream()
                .map(userMapper::mapUserToUserDto)
                .peek(userDto -> userDto.setPassword(null))
                .collect(Collectors.toList()), is(result));
    }

    @Test
    void getUserTest() {
        User testUser = TestUtil.createTestUser();

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        UserDto result = userService.getUser(testUser.getId());

        assertThat(testUser, hasProperty("email", equalTo(result.getEmail())));
    }

    @Test
    void getUserUserNotFoundTest() {
        User testUser = TestUtil.createTestUser();

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getUser(testUser.getId()));
    }

    @Test
    void getUsersOrderTest() throws ParseException {
        User testUserWithOrderList = TestUtil.createTestUserWithOrderList();

        List<OrderDto> expectedOrderList = testUserWithOrderList.getUsersOrder().stream()
                .map(orderMapper::orderToOrderDto)
                .peek(orderDto -> orderDto.getOrderMaster().setPassword(null))
                .collect(Collectors.toList());

        when(userRepository.findById(testUserWithOrderList.getId())).thenReturn(Optional.of(testUserWithOrderList));

        List<OrderDto> userOrder = userService.getUserOrder(testUserWithOrderList.getId());

        assertThat(userOrder, is(expectedOrderList));
    }

    @Test
    void getUsersOrderUserNotFound() {
        User testUser = TestUtil.createTestUser();

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getUserOrder(testUser.getId()));
    }

    @Test
    void payForOrderTest() throws ParseException {
        int testOrderId = 3;
        User testUserWithOrderList = TestUtil.createTestUserWithOrderList();

        when(userRepository.findById(testUserWithOrderList.getId())).thenReturn(Optional.of(testUserWithOrderList));
        when(userRepository.save(testUserWithOrderList)).thenReturn(testUserWithOrderList);

        userService.payForOrder(testUserWithOrderList.getId(), testOrderId);

        assertThat(Objects.requireNonNull(testUserWithOrderList.getUsersOrder().stream()
                .filter(order -> order.getId() == testOrderId)
                .findFirst().orElse(null)).getOrderStatus(), equalTo(Status.PAID));
    }

    @Test
    void payForOrderOrderIsNotReadyToPay() throws ParseException {
        int testOrderId = 2;
        User testUserWithOrderList = TestUtil.createTestUserWithOrderList();

        when(userRepository.findById(testUserWithOrderList.getId())).thenReturn(Optional.of(testUserWithOrderList));

        assertThrows(IllegalStateException.class,
                () -> userService.payForOrder(testUserWithOrderList.getId(), testOrderId));
    }

    @Test
    void payForOrderUserNotFoundTest() {
        int testId = 555;

        when(userRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.payForOrder(testId, anyInt()));
    }

    @Test
    void payForOrderOrderNotFound() throws ParseException {
        int testOrderId = 333;
        User testUserWithOrderList = TestUtil.createTestUserWithOrderList();

        when(userRepository.findById(testUserWithOrderList.getId())).thenReturn(Optional.of(testUserWithOrderList));


        assertThrows(EntityNotFoundException.class,
                () -> userService.payForOrder(testUserWithOrderList.getId(), testOrderId));
    }

    @Test
    void deleteUserTest() {
        int testUserId = 234;

        doNothing().when(userRepository).deleteById(testUserId);

        userService.deleteUser(testUserId);

        verify(userRepository, times(1)).deleteById(testUserId);
    }
}
