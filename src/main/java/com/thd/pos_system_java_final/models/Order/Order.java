package com.thd.pos_system_java_final.models.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity(name = "customer_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    public int idProduct;
    public String customerPhone;

    public Date date;
    public int price;
}
