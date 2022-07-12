package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "Management user entity")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(method = "GET",
            summary = "Get all users",
            responses = @ApiResponse(responseCode = "200"))
    List<UserDto> getUsers() {
        log.info("Get all user");
        return userService.getAllUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/order")
    @Operation(method = "GET",
            summary = "Get users order",
            description = "Add user id to path variable and execute request",
            responses = @ApiResponse(responseCode = "200"))
    public List<OrderDto> getUserOrder(@PathVariable int id) {
        log.info("Get users order, user id {}", id);
        return userService.getUserOrder(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(method = "POST",
            summary = "Create new User",
            description = "Fill all field in RequestBody entity",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User entity for create"),
            responses = @ApiResponse(responseCode = "201"))
    UserDto createUser(@RequestBody @Validated UserDto userDto) {
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
    @Operation(method = "DELETE",
            summary = "Delete user",
            description = "Add user id to path variable and execute request",
            responses = @ApiResponse(responseCode = "200"))
    ResponseEntity<Void> deleteUser(@PathVariable int id) {
        log.info("Delete user by id {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
