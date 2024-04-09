package com.thd.pos_system_java_final.models.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@Data
public class FirstTimePurchaseCoupon extends Coupon{
    private int customerId;
}
