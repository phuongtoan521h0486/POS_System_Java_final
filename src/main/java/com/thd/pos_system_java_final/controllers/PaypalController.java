package com.thd.pos_system_java_final.controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Cart.Item;
import com.thd.pos_system_java_final.models.Customer.Customer;
import com.thd.pos_system_java_final.models.Customer.CustomerRepository;
import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderDetail;
import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.CartService;
import com.thd.pos_system_java_final.services.ImageService;
import com.thd.pos_system_java_final.services.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class PaypalController {
    @Autowired
    PaypalService service;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartService cartService;
    @Autowired
    private HttpSession session;

    @PostMapping("/paypal")
    public String payment(Double total) {
        try {
            Payment payment = service.createPayment(total);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }
}
