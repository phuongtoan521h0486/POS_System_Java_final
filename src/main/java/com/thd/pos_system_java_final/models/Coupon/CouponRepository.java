package com.thd.pos_system_java_final.models.Coupon;

import com.thd.pos_system_java_final.models.Product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CouponRepository extends CrudRepository<Coupon, Integer> {
    Coupon findByCode(String code);
    @Query("SELECT c.code FROM Coupon c")
    List<String> findAllCodes();
}
