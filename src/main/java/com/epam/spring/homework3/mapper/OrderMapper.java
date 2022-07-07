package com.epam.spring.homework3.mapper;

import com.epam.spring.homework3.dto.OrderDto;
import com.epam.spring.homework3.dto.OrderDtoWithInfo;
import com.epam.spring.homework3.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDtoWithInfo orderToOrderDtoWithInfo(Order order);

    Order orderDtoToOrder(OrderDto orderDto);
}
