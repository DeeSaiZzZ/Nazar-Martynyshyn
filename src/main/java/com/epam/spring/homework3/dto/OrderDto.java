package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"userId", "masterId", "favorId"}, allowSetters = true)
public class OrderDto {

    private int id;
    private int userId;
    private int masterId;
    private int favorId;

    private UserDto orderUser;
    private MasterDto orderMaster;
    private FavorDto orderFavor;

    private Status orderStatus;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy HH:mm"
    )
    private Date timeSlot;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy"
    )
    private Date completeDate;
}
