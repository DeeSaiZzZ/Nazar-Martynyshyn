package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.enums.Speciality;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class FavorDto {

    private int id;

    @NotBlank(message = "Name shouldn't be empty")
    private String name;

    private Speciality speciality;

    @Positive(message = "Price cannot be negative")
    private int price;
}
