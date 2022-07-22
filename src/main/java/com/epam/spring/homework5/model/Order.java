package com.epam.spring.homework5.model;

import com.epam.spring.homework5.model.enums.Status;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Updatable<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User orderUser;

    @ManyToOne
    @JoinColumn(name = "master_id", referencedColumnName = "id")
    private Master orderMaster;

    @ManyToOne
    @JoinColumn(name = "favor_id", referencedColumnName = "id")
    private Favor orderFavor;

    @Enumerated(value = EnumType.STRING)
    private Status orderStatus;
    private Date timeSlot;
    private Date completeDate;

    public void update(Order order) {
        this.orderUser = order.orderUser;
        this.orderMaster = order.orderMaster;
        this.orderFavor = order.orderFavor;
        this.orderStatus = order.orderStatus;
        this.timeSlot = order.timeSlot;
        this.completeDate = order.completeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
