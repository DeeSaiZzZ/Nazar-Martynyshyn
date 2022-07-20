package com.epam.spring.homework4.repository;

import com.epam.spring.homework4.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select o from Order o where o.orderUser.id=:user_id")
    List<Order> findAllOrderByUserId(@Param("user_id") Integer userId);
}
