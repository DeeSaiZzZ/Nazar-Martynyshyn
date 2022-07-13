package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    void deleteOrder(int id);

    OrderDto updateOrder(int id, OrderDto orderDto);

    List<OrderDto> getAllOrder();
}
