package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.dto.OrderDtoWithInfo;
import com.epam.spring.homework3.mapper.OrderMapper;
import com.epam.spring.homework3.model.Order;
import com.epam.spring.homework3.repository.FavorRepository;
import com.epam.spring.homework3.repository.MasterRepository;
import com.epam.spring.homework3.repository.OrderRepository;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MasterRepository masterRepository;
    private final FavorRepository favorRepository;
    private final OrderMapper mapper;

    @Override
    public OrderDtoWithInfo createOrder(OrderDto orderDto) {
        log.info("Start create order");
        Order order = mapper.orderDtoToOrder(orderDto);

        order.setOrderFavor(favorRepository.getFavor(order.getFavorId()));
        order.setOrderMaster(masterRepository.getMaster(order.getMasterId()));
        order.setOrderUser(userRepository.getUserById(order.getUserId()));

        log.info("New order entity - {}", order);

        order = orderRepository.addOrder(order);
        return mapper.orderToOrderDtoWithInfo(order);
    }

    @Override
    public void deleteOrder(int id) {
        log.info("Delete order by id {}", id);
        orderRepository.deleteOrder(id);
    }

    @Override
    public OrderDtoWithInfo updateOrder(int id, OrderDto orderDto) {
        log.info("Update order by id {}", id);
        Order order = mapper.orderDtoToOrder(orderDto);

        order.setOrderFavor(favorRepository.getFavor(order.getFavorId()));
        order.setOrderMaster(masterRepository.getMaster(order.getMasterId()));
        order.setOrderUser(userRepository.getUserById(order.getUserId()));

        log.trace("New data - {}", order);

        order = orderRepository.updateOrder(id, order);
        return mapper.orderToOrderDtoWithInfo(order);
    }

    @Override
    public List<OrderDtoWithInfo> getAllOrder() {
        log.info("Get all order start");
        return orderRepository.getAllOrder()
                .stream()
                .map(mapper::orderToOrderDtoWithInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDtoWithInfo> getOrderByUserId(int id) {
        return orderRepository.getOrderByUserId(id).stream()
                .map(mapper::orderToOrderDtoWithInfo)
                .collect(Collectors.toList());
    }
}
