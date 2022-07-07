package com.epam.spring.homework3.model;

import com.epam.spring.homework3.model.enums.Speciality;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Master extends User {
    private double rate;
    private Speciality speciality;
}
