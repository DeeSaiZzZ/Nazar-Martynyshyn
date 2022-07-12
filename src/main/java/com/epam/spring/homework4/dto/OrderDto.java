package com.epam.spring.homework4.dto;

import com.epam.spring.homework4.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    private UserDto orderUser;
    private MasterDto orderMaster;
    private FavorDto orderFavor;

    private Status orderStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    @Schema(description = "Format dd.MM.yyyy HH:mm")
    private Date timeSlot;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Schema(description = "Format dd.MM.yyyy")
    private Date completeDate;
}
