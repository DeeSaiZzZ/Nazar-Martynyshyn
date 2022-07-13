package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.enums.Speciality;
import lombok.Data;

@Data
public class FavorDto {
    private int id;
    private String name;
    private Speciality speciality;
    private int price;
}
