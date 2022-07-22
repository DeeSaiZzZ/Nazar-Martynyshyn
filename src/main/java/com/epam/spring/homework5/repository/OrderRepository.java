package com.epam.spring.homework5.repository;

import com.epam.spring.homework5.model.Order;
import com.epam.spring.homework5.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select count(o) from Order o where o.orderStatus=:status")
    long countOrderByStatus(@Param(value = "status") Status status);

}
