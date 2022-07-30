package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.dto.UserDto;
import com.epam.spring.homework5.mapper.OrderMapperImpl;
import com.epam.spring.homework5.model.enums.Status;
import com.epam.spring.homework5.service.OrderService;
import com.epam.spring.homework5.test.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private OrderMapperImpl orderMapper;

    @Test
    void getOrdersTest() throws Exception {
        List<OrderDto> testOrderList = TestUtil.createTestOrderList()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());

        when(orderService.getAllOrder()).thenReturn(testOrderList);

        mockMvc.perform(get("/order"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(testOrderList.size()));
    }

    @Test
    void createOrderTest() throws Exception {
        OrderDto orderDto = orderMapper.orderToOrderDto(TestUtil.createTestOrder());
        orderDto.setOrderStatus(null);

        when(orderService.createOrder(orderDto)).thenReturn(orderDto);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderMaster").value(orderDto.getOrderMaster()))
                .andExpect(jsonPath("$.orderFavor").value(orderDto.getOrderFavor()));
    }

    @Test
    void createOrderNoValidModelTest() throws Exception {
        OrderDto orderDto = new OrderDto();
        int expectedNumOfError = 6;

        orderDto.setOrderUser(new UserDto());
        orderDto.setOrderStatus(Status.FREE);
        orderDto.setCompleteDate(new Date());

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(expectedNumOfError));
    }

    @Test
    void updateOrderTest() throws Exception {
        OrderDto orderDto = orderMapper.orderToOrderDto(TestUtil.createTestOrder());
        orderDto.setOrderStatus(null);

        when(orderService.updateOrder(orderDto.getId(), orderDto)).thenReturn(orderDto);

        mockMvc.perform(put("/order/" + orderDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderMaster").value(orderDto.getOrderMaster()))
                .andExpect(jsonPath("$.orderFavor").value(orderDto.getOrderFavor()));
    }

    @Test
    void updateOrderNoValidModelTest() throws Exception {
        OrderDto orderDto = orderMapper.orderToOrderDto(TestUtil.createTestOrder());

        mockMvc.perform(put("/order/" + orderDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value("must be null"));
    }

    @Test
    void regUserOnOrderTest() throws Exception {
        int testOrderId = 35;
        int testUserId = 53;

        doNothing().when(orderService).regUserOnOrder(testOrderId, testUserId);

        mockMvc.perform(patch("/order/" + testOrderId)
                        .param("userId", String.valueOf(testUserId)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).regUserOnOrder(testOrderId, testUserId);
    }

    @Test
    void getNumOfOrderByStatusTest() throws Exception {
        Status status = Status.FREE;

        long expectedNumOfOrder = TestUtil.createTestOrderList().stream()
                .filter(order -> order.getOrderStatus().equals(status))
                .count();

        when(orderService.countOrderByStatus(status)).thenReturn(expectedNumOfOrder);

        mockMvc.perform(get("/order/num")
                        .param("status", status.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedNumOfOrder));
    }

    @Test
    void deleteOrderTest() throws Exception {
        doNothing().when(orderService).deleteOrder(anyInt());

        mockMvc.perform(delete("/order/" + anyInt()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(anyInt());
    }
}
