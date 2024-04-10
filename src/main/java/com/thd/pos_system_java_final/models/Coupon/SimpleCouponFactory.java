package com.thd.pos_system_java_final.models.Coupon;

import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.shared.ultils.CouponCodeGenerator;

import java.util.Date;

public class SimpleCouponFactory {
    public static Coupon createCoupon(CouponType type, double value, Order order) {
        Coupon coupon;
        switch (type) {
            case FIXED_AMOUNT:
                coupon = new FixedAmountCoupon(value);
                coupon.setEndDate(new Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L)); // 30 days
                coupon.setMinOrderAmount(value);
                break;
            case PERCENTAGE:
                coupon = new PercentageCoupon(value);
                coupon.setEndDate(new Date(System.currentTimeMillis() + 90 * 24 * 60 * 60 * 1000L)); // 90 days
                coupon.setMinOrderAmount(999);
                break;
            default:
                return null;
        }
        coupon.setStartDate(new Date());
        coupon.setCouponType(type);
        coupon.setCode(CouponCodeGenerator.generateRandomCode());
        coupon.setActive(false);
        coupon.setOrderId(order.getOrderId());
        return coupon;
    }
}
