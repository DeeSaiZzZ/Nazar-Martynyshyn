package com.epam.spring.homework5.service;

import com.epam.spring.homework5.dto.FavorDto;
import com.epam.spring.homework5.dto.MasterDto;
import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.dto.UserDto;
import com.epam.spring.homework5.mapper.FavorMapperImpl;
import com.epam.spring.homework5.mapper.MasterMapperImpl;
import com.epam.spring.homework5.mapper.OrderMapperImpl;
import com.epam.spring.homework5.mapper.UserMapperImpl;
import com.epam.spring.homework5.model.Order;
import com.epam.spring.homework5.model.enums.Status;
import com.epam.spring.homework5.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework5.model.exeptions.IllegalStateException;
import com.epam.spring.homework5.repository.OrderRepository;
import com.epam.spring.homework5.service.impl.OrderServiceImpl;
import com.epam.spring.homework5.test.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MasterService masterService;

    @Mock
    private FavorService favorService;

    @Mock
    private UserService userService;

    @Spy
    private OrderMapperImpl orderMapper;

    @Spy
    private MasterMapperImpl masterMapper;

    @Spy
    private FavorMapperImpl favorMapper;

    @Spy
    private UserMapperImpl userMapper;

    @Test
    void createOrderTest() throws ParseException {
        Order testOrder = TestUtil.createTestOrder();

        when(orderRepository.save(testOrder)).thenReturn(testOrder);

        OrderDto result = orderService.createOrder(orderMapper.orderToOrderDto(testOrder));

        testOrder.getOrderMaster().setPassword(null);
        assertThat(result, equalTo(orderMapper.orderToOrderDto(testOrder)));
    }

    @Test
    void updateOrderTest() throws ParseException {
        Order testOrder = TestUtil.createTestOrder();

        FavorDto favorDto = favorMapper.favorToFavorDto(testOrder.getOrderFavor());
        favorDto.setName("NewFavorNameForTest");
        MasterDto masterDto = masterMapper.masterToMasterDto(testOrder.getOrderMaster());

        Order newOrderModel = TestUtil.createTestOrder();

        when(masterService.getMaster(masterDto.getId())).thenReturn(masterDto);
        when(favorService.getFavor(favorDto.getId())).thenReturn(favorDto);

        when(orderRepository.findById(testOrder.getId())).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(testOrder)).thenReturn(testOrder);

        OrderDto orderDto = orderService.updateOrder(testOrder.getId(), orderMapper.orderToOrderDto(newOrderModel));

        assertThat(orderDto.getOrderFavor().getName(), equalTo(favorDto.getName()));
    }

    @Test
    void updateOrderOrderNotExit() throws ParseException {
        Order testOrder = TestUtil.createTestOrder();

        when(orderRepository.findById(testOrder.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> orderService.updateOrder(testOrder.getId(), orderMapper.orderToOrderDto(testOrder)));
    }

    @Test
    void getAllOrderTest() throws ParseException {
        List<Order> testOrderList = TestUtil.createTestOrderList();

        when(orderRepository.findAll()).thenReturn(testOrderList);

        List<OrderDto> result = orderService.getAllOrder();

        assertThat(result, equalTo(testOrderList.stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList())));
    }

    @Test
    void regUserOnOrderTest() throws ParseException {
        Order testOrder = TestUtil.createTestOrder();
        UserDto testUser = userMapper.mapUserToUserDto(TestUtil.createTestUser());

        when(orderRepository.findById(testOrder.getId())).thenReturn(Optional.of(testOrder));
        when(userService.getUser(testUser.getId())).thenReturn(testUser);
        when(orderRepository.save(testOrder)).thenReturn(testOrder);

        orderService.regUserOnOrder(testOrder.getId(), testUser.getId());

        assertThat(testOrder, allOf(
                hasProperty("orderUser", equalTo(userMapper.mapUserDtoToUser(testUser))),
                hasProperty("orderStatus", equalTo(Status.AWAITING_PAYMENT))
        ));
    }

    @Test
    void regUserOnOrderOrderNotFoundTest() throws ParseException {
        Order testOrder = TestUtil.createTestOrder();

        when(orderRepository.findById(testOrder.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> orderService.regUserOnOrder(testOrder.getId(), anyInt()));
    }

    @Test
    void regUserOnOrderOrderIsNotFreeTest() throws ParseException {
        Order testOrder = TestUtil.createTestOrder();
        testOrder.setOrderStatus(Status.AWAITING_PAYMENT);

        when(orderRepository.findById(testOrder.getId())).thenReturn(Optional.of(testOrder));

        assertThrows(IllegalStateException.class,
                () -> orderService.regUserOnOrder(testOrder.getId(), anyInt()));
    }

    @Test
    void countOrderByStatusTest() throws ParseException {
        List<Order> expected = TestUtil.createTestOrderList().stream()
                .filter(order -> order.getOrderStatus().equals(Status.FREE))
                .collect(Collectors.toList());

        when(orderRepository.countOrderByStatus(Status.FREE)).thenReturn((long) expected.size());

        long result = orderService.countOrderByStatus(Status.FREE);

        assertThat(result, equalTo((long) expected.size()));
    }

    @Test
    void deleteOrderTest() {
        int testId = 443;

        doNothing().when(orderRepository).deleteById(testId);

        orderService.deleteOrder(testId);

        verify(orderRepository, times(1)).deleteById(testId);
    }
}
