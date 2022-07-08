package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("Create user, request body {}", userDto);
        return userService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Get all user");
        return userService.getAllUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        log.info("Delete user by id {}", id);
        userService.deleteUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/order")
    List<OrderDto> getUserOrder(@PathVariable int id) {
        log.info("Get users order, user id {}", id);
        return userService.getUserOrder(id);
    }

}
