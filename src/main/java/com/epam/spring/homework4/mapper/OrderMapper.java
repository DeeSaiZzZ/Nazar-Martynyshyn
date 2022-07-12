package com.epam.spring.homework4.mapper;

import com.epam.spring.homework4.dto.OrderDto;
import com.epam.spring.homework4.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);
}
