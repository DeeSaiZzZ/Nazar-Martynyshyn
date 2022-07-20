package com.epam.spring.homework4.model;

import com.epam.spring.homework4.model.enums.Speciality;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "masters")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Master extends User {
    private double rate;
    @Enumerated(value = EnumType.STRING)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Master master = (Master) o;
        return getId() != null && Objects.equals(getId(), master.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
