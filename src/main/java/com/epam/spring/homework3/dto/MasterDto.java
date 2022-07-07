package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.enums.Speciality;
import lombok.Data;

@Data
public class MasterDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double rate;
    private Speciality speciality;
}
