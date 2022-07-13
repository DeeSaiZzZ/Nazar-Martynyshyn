package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.mapper.OrderMapper;
import com.epam.spring.homework3.model.Order;
import com.epam.spring.homework3.repository.OrderRepository;
import com.epam.spring.homework3.service.FavorService;
import com.epam.spring.homework3.service.MasterService;
import com.epam.spring.homework3.service.OrderService;
import com.epam.spring.homework3.service.UserService;
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

    private final UserService userService;
    private final MasterService masterService;
    private final FavorService favorService;

    private final OrderMapper mapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("Start create order");

        orderDto.setOrderFavor(favorService.getFavor(orderDto.getOrderFavor().getId()));
        orderDto.setOrderMaster(masterService.getMaster(orderDto.getOrderMaster().getId()));
        orderDto.setOrderUser(userService.getUser(orderDto.getOrderUser().getId()));

        Order order = mapper.orderDtoToOrder(orderDto);
        log.info("New order entity - {}", order);
        order = orderRepository.createOrder(order);
        return mapper.orderToOrderDto(order);
    }

    @Override
    public void deleteOrder(int id) {
        log.info("Delete order by id {}", id);
        orderRepository.deleteOrder(id);
    }

    @Override
    public OrderDto updateOrder(int id, OrderDto orderDto) {
        log.info("Update order by id {}", id);

        orderDto.setOrderFavor(favorService.getFavor(orderDto.getOrderFavor().getId()));
        orderDto.setOrderMaster(masterService.getMaster(orderDto.getOrderMaster().getId()));
        orderDto.setOrderUser(userService.getUser(orderDto.getOrderUser().getId()));

        Order order = mapper.orderDtoToOrder(orderDto);
        log.trace("New data - {}", order);
        order = orderRepository.updateOrder(id, order);
        return mapper.orderToOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrder() {
        log.info("Get all order start");
        return orderRepository.getAllOrder()
                .stream()
                .map(mapper::orderToOrderDto)
                .collect(Collectors.toList());
    }
}
