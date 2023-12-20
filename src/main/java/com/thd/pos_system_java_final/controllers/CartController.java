package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.Cart.Cart;
import com.thd.pos_system_java_final.models.Customer.Customer;
import com.thd.pos_system_java_final.models.Customer.CustomerRepository;
import com.thd.pos_system_java_final.models.Product.Product;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/step-1")
    public String step1(HttpSession session) {
//        Object cartAttribute = session.getAttribute("cart");
//
//        if (cartAttribute != null) {
//            Map<Product, Integer> cart = (Map<Product, Integer>) cartAttribute;
//            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
//                Product product = entry.getKey();
//                Integer quantity = entry.getValue();
//                System.out.println("Product: " + product.getProductName() + ", Quantity: " + quantity);
//            }
//        } else {
//            System.out.println("Cart is null or empty.");
//        }
        return "POS/step1";
    }

    @PostMapping("/step-1")
    public String step1(String phone, Model model) {
        Customer customer = customerRepository.findByPhoneNumber(phone);
        System.out.println(customer.toString());
        model.addAttribute("customer", customer);
        return "POS/step2";
    }

    @GetMapping("/step-2")
    @ResponseBody
    public String step2() {
        return "Not support this method";
    }

//    @PostMapping("/step-2")
//    public String step2(String phone, Model model, HttpSession session) {
//
//        return "POS/step3";
//    }

    @PostMapping("/saveCart")
    @ResponseBody
    public String saveCart(@RequestBody Cart cartData, HttpSession session) {
        if (cartData != null && cartData.getMyCart() != null) {
            Map<Integer, Integer> cartDataMap = cartData.getMyCart();
            Map<Product, Integer> cart = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry : cartDataMap.entrySet()) {
                Integer productId = entry.getKey();
                Product product = productRepository.findById(productId).orElse(null);

                if (product != null) {
                    cart.put(product, entry.getValue());
                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }
            }

            session.setAttribute("cart", cart);
            return "Cart saved successfully";
        } else {
            // Handle the case when cartData or myCart is null
            return "Invalid cart data provided";
        }
    }


}

