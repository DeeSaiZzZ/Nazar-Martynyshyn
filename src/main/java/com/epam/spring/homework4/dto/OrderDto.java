package com.epam.spring.homework4.dto;

import com.epam.spring.homework4.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
public class OrderDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @Null
    private UserDto orderUser;
    @NotNull
    private MasterDto orderMaster;
    @NotNull
    private FavorDto orderFavor;
    @Null
    private Status orderStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    @Schema(description = "Format dd.MM.yyyy HH:mm")
    @NotNull
    private Date timeSlot;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Schema(description = "Format dd.MM.yyyy")
    @Null
    private Date completeDate;
}
