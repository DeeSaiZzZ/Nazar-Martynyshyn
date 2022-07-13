package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<OrderDto> getOrder() {
        log.info("Get all order");
        return orderService.getAllOrder();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    OrderDto createOrder(@RequestBody OrderDto orderDto) {
        log.info("Create order, request body {}", orderDto);
        return orderService.createOrder(orderDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    OrderDto updateOrder(@PathVariable int id, @RequestBody OrderDto orderDto) {
        log.info("Update order with id {} new entity {}", id, orderDto);
        return orderService.updateOrder(id, orderDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        log.info("Delete order by id {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
