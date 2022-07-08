package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.enums.Speciality;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(allowSetters = true, value = "password")
public class MasterDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double rate;
    private Speciality speciality;
}
