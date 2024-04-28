package com.thd.pos_system_java_final.models.Coupon;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends CrudRepository<Coupon, Integer> {
    Coupon findByCode(String code);

    @Query("SELECT c.code FROM Coupon c")
    List<String> findAllCodes();

    @Query("SELECT c.code FROM Coupon c WHERE c.orderId = :orderId")
    String findCodeByOrderId(@Param("orderId") int orderId);
}
