package com.thd.pos_system_java_final.models.Order;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {
    List<OrderDetail> findAll();

    List<OrderDetail> findAllByOrderId(int orderId);

    boolean existsByProductId(int productId);

}
