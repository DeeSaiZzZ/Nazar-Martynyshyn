package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<UserDto> getUsers() {
        log.info("Get all user");
        return userService.getAllUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/order")
    List<OrderDto> getUserOrder(@PathVariable int id) {
        log.info("Get users order, user id {}", id);
        return userService.getUserOrder(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    UserDto getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserDto createUser(@RequestBody UserDto userDto) {
        log.info("Create user, request body {}", userDto);
        return userService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    UserDto updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable int id) {
        log.info("Delete user by id {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
