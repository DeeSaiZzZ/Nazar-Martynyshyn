package com.epam.spring.homework5.service;

import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.model.enums.Status;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    void deleteOrder(int id);

    OrderDto updateOrder(int id, OrderDto orderDto);

    List<OrderDto> getAllOrder();

    void regUserOnOrder(int id, int userId);

    long countOrderByStatus(Status status);
}
