package com.epam.spring.homework4.dto;

import com.epam.spring.homework4.model.enums.Speciality;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class FavorDto {

    private int id;

    @NotBlank(message = "{favor.name.NotBlank}")
    private String name;

    private Speciality speciality;

    @Positive(message = "{favor.price.Positive}")
    private int price;
}
