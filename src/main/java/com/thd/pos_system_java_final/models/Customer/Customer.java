package com.thd.pos_system_java_final.models.Customer;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    public String name;
    @NotNull
    public String phoneNumber;
    public String address;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;
}
