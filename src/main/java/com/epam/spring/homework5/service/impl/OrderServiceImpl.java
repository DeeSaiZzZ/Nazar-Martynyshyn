package com.epam.spring.homework5.service.impl;

import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.mapper.OrderMapper;
import com.epam.spring.homework5.mapper.UserMapper;
import com.epam.spring.homework5.model.Order;
import com.epam.spring.homework5.model.User;
import com.epam.spring.homework5.model.enums.Status;
import com.epam.spring.homework5.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework5.model.exeptions.IllegalStateException;
import com.epam.spring.homework5.repository.OrderRepository;
import com.epam.spring.homework5.service.FavorService;
import com.epam.spring.homework5.service.MasterService;
import com.epam.spring.homework5.service.OrderService;
import com.epam.spring.homework5.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final MasterService masterService;
    private final FavorService favorService;
    private final UserService userService;

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) throws EntityNotFoundException {
        log.info("Start create order");
        orderDto.setOrderMaster(masterService.getMaster(orderDto.getOrderMaster().getId()));
        orderDto.setOrderFavor(favorService.getFavor(orderDto.getOrderFavor().getId()));

        Order order = orderMapper.orderDtoToOrder(orderDto);
        order.setOrderStatus(Status.FREE);
        log.info("New order entity - {}", order);
        order = orderRepository.save(order);

        orderDto = orderMapper.orderToOrderDto(order);
        orderDto.getOrderMaster().setPassword(null);

        return orderDto;
    }

    @Override
    public OrderDto updateOrder(int id, OrderDto orderDto) {
        log.info("Update order by id {}", id);
        orderDto.setOrderMaster(masterService.getMaster(orderDto.getOrderMaster().getId()));
        orderDto.setOrderFavor(favorService.getFavor(orderDto.getOrderFavor().getId()));

        Order order = orderMapper.orderDtoToOrder(orderDto);
        Order persistOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id-%d not found", id)));

        persistOrder.update(order);
        log.trace("New data - {}", persistOrder);
        order = orderRepository.save(persistOrder);

        orderDto = orderMapper.orderToOrderDto(order);
        orderDto.getOrderMaster().setPassword(null);

        return orderDto;
    }

    @Override
    public List<OrderDto> getAllOrder() {
        log.info("Get all order start");
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .peek(orderDto -> {
                    if (orderDto.getOrderUser() != null) {
                        orderDto.getOrderUser().setPassword(null);
                    }
                    orderDto.getOrderMaster().setPassword(null);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void regUserOnOrder(int id, int userId) {
        User user = userMapper.mapUserDtoToUser(userService.getUser(userId));
        Order persistOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id-%d not found", id)));

        if (!persistOrder.getOrderStatus().equals(Status.FREE)) {
            throw new IllegalStateException(String.format("Order {%d} is not free", id));
        }

        persistOrder.setOrderUser(user);
        persistOrder.setOrderStatus(Status.AWAITING_PAYMENT);

        orderRepository.save(persistOrder);
    }

    @Override
    public void deleteOrder(int id) {
        log.info("Delete order by id {}", id);
        orderRepository.deleteById(id);
    }
}
