package com.epam.spring.homework4.controller;

import com.epam.spring.homework4.dto.OrderDto;
import com.epam.spring.homework4.dto.groups.OnCreate;
import com.epam.spring.homework4.service.OrderService;
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
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order controller", description = "Manage order entity")
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(method = "GET",
            summary = "Get all order",
            responses = @ApiResponse(responseCode = "200"))
    List<OrderDto> getOrder() {
        log.info("Get all order");
        return orderService.getAllOrder();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(method = "POST",
            summary = "Create order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Fill field id in orderUser, " +
                    "orderMaster, orderFavor"),
            responses = @ApiResponse(responseCode = "201"))
    OrderDto createOrder(@RequestBody @Validated(OnCreate.class) OrderDto orderDto) {
        log.info("Create order, request body {}", orderDto);
        return orderService.createOrder(orderDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(method = "PUT",
            summary = "Update order entity",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Fill field id in orderUser, " +
                    "orderMaster, orderFavor, orderStatus,timeSlot, completeDate"),
            responses = @ApiResponse(responseCode = "200"))
    OrderDto updateOrder(@PathVariable int id, @RequestBody OrderDto orderDto) {
        log.info("Update order with id {} new entity {}", id, orderDto);
        return orderService.updateOrder(id, orderDto);
    }

    @DeleteMapping("/{id}")
    @Operation(method = "DELETE",
            summary = "Delete order by id",
            responses = @ApiResponse(responseCode = "200"))
    ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        log.info("Delete order by id {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
