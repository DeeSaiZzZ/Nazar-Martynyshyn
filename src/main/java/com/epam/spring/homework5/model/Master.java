package com.epam.spring.homework5.model;

import com.epam.spring.homework5.model.enums.Role;
import com.epam.spring.homework5.model.enums.Speciality;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "masters")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Master extends User {

    public Master(Integer id, String name, String surname, String email, String password,
                  List<Order> usersOrder, Role role, double rate, List<Order> timeTable, Speciality speciality) {
        super(id, name, surname, email, password, usersOrder, role);
        this.rate = rate;
        this.timeTable = timeTable;
        this.speciality = speciality;
    }

    private double rate;

    @OneToMany(mappedBy = "orderMaster", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> timeTable;

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
