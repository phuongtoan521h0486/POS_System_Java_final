package com.thd.pos_system_java_final.models.Coupon;
import com.thd.pos_system_java_final.models.Order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PercentageCoupon extends Coupon{
    private double percentageDiscount;

    @Override
    public double getAmountDiscount(double totalAmount) {
        return (percentageDiscount / 100) * totalAmount;
    }
}
