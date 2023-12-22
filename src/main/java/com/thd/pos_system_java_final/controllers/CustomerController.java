package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.Customer.Customer;
import com.thd.pos_system_java_final.models.Customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/check")
    public ResponseEntity<Customer> checkCustomer(@RequestParam String phone) {
        Customer customer = customerRepository.findByPhoneNumber(phone);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        System.out.println(customer.toString());
        if (customer.getName() == null || customer.getAddress() == null) {
            return ResponseEntity.badRequest().body("Full name and address are required.");
        }

        if (StringUtils.isEmpty(customer.getName()) || StringUtils.isEmpty(customer.getAddress())) {
            return ResponseEntity.badRequest().body("Full name and address are required.");
        }

        Customer newCustomer = new Customer();
        newCustomer.setName(customer.getName());
        newCustomer.setAddress(customer.getAddress());
        newCustomer.setPhoneNumber(customer.getPhoneNumber());

        customerRepository.save(newCustomer);

        return ResponseEntity.ok("New customer created successfully.");
    }
}
