package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Coupon.Coupon;
import com.thd.pos_system_java_final.models.Coupon.CouponRepository;
import com.thd.pos_system_java_final.models.Coupon.CouponType;
import com.thd.pos_system_java_final.models.Coupon.SimpleCouponFactory;
import com.thd.pos_system_java_final.models.Order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;


@Service
public class CouponService {
    @Autowired
    private CouponRepository repo;

    public Coupon getCouponByCode(String code) {
        return repo.findByCode(code);
    }

    public void addCoupon(CouponType type, double valueDiscount, Order order) {
        Coupon coupon = SimpleCouponFactory.createCoupon(type, valueDiscount, order);
        if (coupon != null) {
            repo.save(coupon);
        }
    }

    public String getCouponCodeByOrderId(int orderId) {
        return repo.findCodeByOrderId(orderId);
    }

    public double getDiscountAmount(String code, double totalAmount) {
        return getCouponByCode(code).getAmountDiscount(totalAmount);
    }

    public String getStatus(String code, double totalAmount) {
        Coupon coupon = getCouponByCode(code);
        if (coupon == null) return "Your coupon is invalid";

        LocalDate today = LocalDate.now();

        Date startDate = coupon.getStartDate();
        Date endDate = coupon.getEndDate();

        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (!(localStartDate.isBefore(today) || localEndDate.isAfter(today))) {
            return "Your coupon has expired";
        } else if (coupon.isActive()) {
            return "Your coupon is used";
        } else if (totalAmount < coupon.getMinOrderAmount()) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            String formattedMinOrderAmount = currencyFormat.format(coupon.getMinOrderAmount());
            return String.format("Your coupon must apply for orders with total amount greater than %s",
                    formattedMinOrderAmount);
        }
        return "Apply coupon successful";
    }
}
