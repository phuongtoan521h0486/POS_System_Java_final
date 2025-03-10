package com.thd.pos_system_java_final.models.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "customer_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private Date orderDate;
    private double totalAmount;
    private double givenMoney;
    private double excessMoney;
    private int quantity;
    private int customerId;
    private int accountId;

    @Column(name = "couponCode")
    private String couponCode;
}
