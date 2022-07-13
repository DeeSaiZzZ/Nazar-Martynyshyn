package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.Order;

import java.util.List;

public interface OrderRepository {
    Order createOrder(Order order);

    void deleteOrder(int id);

    Order updateOrder(int id, Order order);

    List<Order> getAllOrder();
}
