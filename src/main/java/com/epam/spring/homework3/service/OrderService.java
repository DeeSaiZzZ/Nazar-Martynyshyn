package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.dto.OrderDtoWithInfo;

import java.util.List;

public interface OrderService {
    OrderDtoWithInfo createOrder(OrderDto orderDto);

    void deleteOrder(int id);

    OrderDtoWithInfo updateOrder(int id, OrderDto orderDto);

    List<OrderDtoWithInfo> getAllOrder();

    List<OrderDtoWithInfo> getOrderByUserId(int id);
}
