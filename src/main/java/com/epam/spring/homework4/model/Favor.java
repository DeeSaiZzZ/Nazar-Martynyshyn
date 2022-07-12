package com.epam.spring.homework4.model;

import com.epam.spring.homework4.model.enums.Speciality;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Favor implements Updatable<Favor> {
    private int id;
    private String name;
    private Speciality speciality;
    private int price;

    @Override
    public void update(Favor entity) {
        this.name = entity.name;
        this.speciality = entity.speciality;
        this.price = entity.price;
    }
}
