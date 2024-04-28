package com.thd.pos_system_java_final.models.Order;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAll();

    int countAllByCustomerId(int customerId);

    List<Order> findAllByCustomerId(int customerId);

    Order findFirstByOrderByOrderDateDesc();

    Order findByOrderId(int orderId);

    List<Order> findByOrderDateBetween(Date fromDate, Date toDate);

    List<Order> findOrdersByAccountId(int id);
}
