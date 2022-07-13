package com.epam.spring.homework3.model;

import com.epam.spring.homework3.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Order implements Updatable<Order> {
    private int id;

    private User orderUser;
    private Master orderMaster;
    private Favor orderFavor;

    private Status orderStatus;
    private LocalDateTime timeSlot;
    private LocalDate completeDate;

    public void update(Order order) {
        this.orderUser = order.orderUser;
        this.orderMaster = order.orderMaster;
        this.orderFavor = order.orderFavor;
        this.orderStatus = order.orderStatus;
        this.timeSlot = order.timeSlot;
        this.completeDate = order.completeDate;
    }

}
