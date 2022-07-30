package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.dto.UserDto;
import com.epam.spring.homework5.mapper.OrderMapperImpl;
import com.epam.spring.homework5.mapper.UserMapperImpl;
import com.epam.spring.homework5.model.User;
import com.epam.spring.homework5.service.UserService;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    UserMapperImpl userMapper;

    @Spy
    OrderMapperImpl orderMapper;

    @Test
    void getUsersTest() throws Exception {
        List<UserDto> userDtoList = TestUtil.createUserTestList().stream()
                .map(userMapper::mapUserToUserDto).collect(Collectors.toList());

        when(userService.getAllUser()).thenReturn(userDtoList);

        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(userDtoList.size()));
    }

    @Test
    void getUserOrderTest() throws Exception {
        User testUser = TestUtil.createTestUserWithOrderList();

        List<OrderDto> usersOrder = testUser.getUsersOrder().stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());

        when(userService.getUserOrder(testUser.getId())).thenReturn(usersOrder);

        mockMvc.perform(get("/user/" + testUser.getId() + "/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(testUser.getUsersOrder().size()));
    }

    @Test
    void payForOrderTest() throws Exception {
        int testUserId = 33;
        int testOrderId = 55;

        doNothing().when(userService).payForOrder(testUserId, testOrderId);

        mockMvc.perform(patch("/user/" + testUserId + "/orders/" + testOrderId))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(userService, times(1)).payForOrder(testUserId, testOrderId);
    }

    @Test
    void getUserTest() throws Exception {
        UserDto testUser = userMapper.mapUserToUserDto(TestUtil.createTestUser());

        when(userService.getUser(testUser.getId())).thenReturn(testUser);

        mockMvc.perform(get("/user/" + testUser.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));
    }

    @Test
    void createUserTest() throws Exception {
        UserDto testUser = userMapper.mapUserToUserDto(TestUtil.createTestUser());
        testUser.setPassword("testPassword12345");

        when(userService.createUser(testUser)).thenReturn(testUser);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));
    }

    @Test
    void createUserNoValidModelTest() throws Exception {
        UserDto userDto = new UserDto();
        int expectedNumOfError = 5;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(expectedNumOfError));
    }

    @Test
    void updateUserTest() throws Exception {
        UserDto testUser = userMapper.mapUserToUserDto(TestUtil.createTestUser());

        when(userService.updateUser(testUser.getId(), testUser)).thenReturn(testUser);

        mockMvc.perform(put("/user/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));
    }

    @Test
    void updateUserNoValidModelTest() throws Exception {
        UserDto testUser = userMapper.mapUserToUserDto(TestUtil.createTestUser());
        testUser.setPassword("testPass");

        String expectedMessage = "Password should be empty when entity update";

        when(userService.updateUser(testUser.getId(), testUser)).thenReturn(testUser);

        mockMvc.perform(put("/user/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value(expectedMessage));
    }

    @Test
    void deleteUserTest() throws Exception {
        doNothing().when(userService).deleteUser(anyInt());

        mockMvc.perform(delete("/user/" + anyInt()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(anyInt());
    }
}
