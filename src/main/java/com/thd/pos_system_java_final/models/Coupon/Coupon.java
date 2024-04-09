package com.thd.pos_system_java_final.models.Coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int couponId;
    @Column(unique = true)
    private String code;
    private double discountAmount;
    private Date startDate;
    private Date endDate;
    private double minOrderAmount;
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private CouponType couponType;
}
