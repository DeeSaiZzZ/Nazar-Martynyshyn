package com.epam.spring.homework4.model;

import com.epam.spring.homework4.model.enums.Speciality;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "favors")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Favor implements Updatable<Favor> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Speciality speciality;

    @Column(nullable = false)
    private int price;

    @Override
    public void update(Favor entity) {
        this.name = entity.name;
        this.speciality = entity.speciality;
        this.price = entity.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Favor favor = (Favor) o;
        return id != null && Objects.equals(id, favor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
