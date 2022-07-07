package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderDto {

    private int id;
    private int userId;
    private int masterId;
    private int favorId;

    private Status orderStatus;
    private LocalDateTime timeSlot;
    private LocalDate completeDate;

}
