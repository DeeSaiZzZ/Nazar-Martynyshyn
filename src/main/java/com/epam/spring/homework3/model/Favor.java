package com.epam.spring.homework3.model;

import com.epam.spring.homework3.model.enums.Speciality;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Favor {
    private int id;
    private String name;
    private Speciality speciality;
    private int price;
}
