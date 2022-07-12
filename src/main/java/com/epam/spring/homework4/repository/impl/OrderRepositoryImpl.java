package com.epam.spring.homework4.repository.impl;

import com.epam.spring.homework4.model.Order;
import com.epam.spring.homework4.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework4.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final List<Order> orderList;
    private int idCounter = 1;

    @Override
    public Order createOrder(Order order) {
        log.info("Create order");
        log.trace("Order id = {}", idCounter);
        order.setId(idCounter++);
        orderList.add(order);
        return order;
    }

    @Override
    public void deleteOrder(int id) {
        log.info("Delete order by id {}", id);
        orderList.removeIf(order -> order.getId() == id);
    }

    @Override
    public Order updateOrder(int id, Order newOrder) {
        log.info("Update order by id {}", id);
        Order updatableOrder = orderList.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
        log.trace("Order before update - {}", updatableOrder);
        updatableOrder.update(newOrder);
        log.trace("Order after update - {}", updatableOrder);
        return updatableOrder;
    }

    @Override
    public List<Order> getAllOrder() {
        log.info("Get all order");
        return new ArrayList<>(orderList);
    }

    @Override
    public List<Order> getOrderByUserId(int id) {
        return orderList.stream()
                .filter(order -> order.getOrderUser().getId() == id)
                .collect(Collectors.toList());
    }
}
