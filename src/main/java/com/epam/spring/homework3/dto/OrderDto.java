package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private int id;

    private UserDto orderUser;
    private MasterDto orderMaster;
    private FavorDto orderFavor;

    private Status orderStatus;
    private Date timeSlot;
    private Date completeDate;
}
