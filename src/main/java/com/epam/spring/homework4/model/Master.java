package com.epam.spring.homework4.model;

import com.epam.spring.homework4.model.enums.Speciality;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Master extends User {
    private double rate;
    private Speciality speciality;

    @Override
    public void update(User entity) {
        super.update(entity);
        if (entity instanceof Master) {
            Master master = (Master) entity;
            this.rate = master.rate;
            this.speciality = master.speciality;
        }
    }
}
