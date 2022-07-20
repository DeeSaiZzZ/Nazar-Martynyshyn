package com.epam.spring.homework4.service.impl;

import com.epam.spring.homework4.dto.OrderDto;
import com.epam.spring.homework4.mapper.OrderMapper;
import com.epam.spring.homework4.model.Order;
import com.epam.spring.homework4.model.enums.Status;
import com.epam.spring.homework4.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework4.repository.OrderRepository;
import com.epam.spring.homework4.service.FavorService;
import com.epam.spring.homework4.service.MasterService;
import com.epam.spring.homework4.service.OrderService;
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
    private final MasterService masterService;
    private final FavorService favorService;
    private final OrderMapper mapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) throws EntityNotFoundException {
        log.info("Start create order");
        orderDto.setOrderMaster(masterService.getMaster(orderDto.getOrderMaster().getId()));
        orderDto.setOrderFavor(favorService.getFavor(orderDto.getOrderFavor().getId()));

        Order order = mapper.orderDtoToOrder(orderDto);
        order.setOrderStatus(Status.FREE);
        log.info("New order entity - {}", order);
        order = orderRepository.save(order);
        return mapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto updateOrder(int id, OrderDto orderDto) {
        log.info("Update order by id {}", id);
        Order order = mapper.orderDtoToOrder(orderDto);
        Order persistOrder = orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        persistOrder.update(order);
        log.trace("New data - {}", order);
        order = orderRepository.save(persistOrder);
        return mapper.orderToOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrder() {
        log.info("Get all order start");
        return orderRepository.findAll()
                .stream()
                .map(mapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(int id) {
        log.info("Delete order by id {}", id);
        orderRepository.deleteById(id);
    }
}
