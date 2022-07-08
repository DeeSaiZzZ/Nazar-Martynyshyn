package com.epam.spring.homework3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(allowSetters = true, value = "password")
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
