package com.thd.pos_system_java_final.models.Cart;


import com.thd.pos_system_java_final.models.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Map<Integer, Integer> myCart;
}

