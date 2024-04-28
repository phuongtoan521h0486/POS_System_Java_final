package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/getStatus")
    public String getStatus(String code, double totalAmount) {
        return couponService.getStatus(code, totalAmount);
    }

    @GetMapping("/getDiscountAmount")
    public double getDiscountAmount(String code, double totalAmount) {
        return couponService.getDiscountAmount(code, totalAmount);
    }
}
