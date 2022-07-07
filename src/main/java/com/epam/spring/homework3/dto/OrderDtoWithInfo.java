package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderDtoWithInfo {
    private int id;

    private UserDto orderUser;
    private MasterDto orderMaster;
    private FavorDto orderFavor;

    private Status orderStatus;
    private LocalDateTime timeSlot;
    private LocalDate completeDate;
}
