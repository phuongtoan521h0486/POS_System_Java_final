package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.deletion.CustomerDeletionController;
import com.thd.pos_system_java_final.deletion.DeletionController;
import com.thd.pos_system_java_final.models.Cart.Item;
import com.thd.pos_system_java_final.models.Coupon.Coupon;
import com.thd.pos_system_java_final.models.Customer.Customer;
import com.thd.pos_system_java_final.models.Customer.CustomerRepository;
import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderDetail;
import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import com.thd.pos_system_java_final.models.Product.Product;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.CouponService;
import com.thd.pos_system_java_final.services.CustomerService;
import com.thd.pos_system_java_final.shared.ultils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CouponService couponService;

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

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        List<Customer> customers = customerRepository.findAll();

        model.addAttribute("utils", new WebUtils());
        model.addAttribute("account", session.getAttribute("account"));

        model.addAttribute("customers", customers);
        model.addAttribute("customerService", customerService);

        return "Customer/index";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        DeletionController del = new CustomerDeletionController(customerRepository);
        return del.delete(id);
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, String name, String phoneNumber, String address) {
        if (name == null || phoneNumber == null || address == null ||
                name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            return "redirect:/customer";
        }

        Customer customer = customerRepository.findByCustomerId(id);
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);

        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model, HttpSession session) {
        Customer customer = customerRepository.findByCustomerId(id);
        model.addAttribute("customer", customer);
        model.addAttribute("totalOrder", customerService.calculateTotalOrder(id));
        model.addAttribute("totalSpend", customerService.calculateTotalSpend(id));
        model.addAttribute("latestOrder", customerService.getLatestOrder());
        model.addAttribute("totalQuantity", customerService.calculateTotalQuantity(id));

        model.addAttribute("orders", orderRepository.findAllByCustomerId(id));
        model.addAttribute("accountService", accountService);

        model.addAttribute("utils", new WebUtils());
        model.addAttribute("account", session.getAttribute("account"));
        return "Customer/detail";
    }

    @GetMapping("/{customerId}/invoice/{orderId}")
    public String bill(@PathVariable int customerId, @PathVariable int orderId, Model model) {
        Order order = orderRepository.findByOrderId(orderId);
        Customer customer = customerRepository.findByCustomerId(customerId);
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderId);
        List<Item> items = new ArrayList<>();
        for(OrderDetail detail: orderDetails) {
            Product p = productRepository.findByProductId(detail.getProductId());
            items.add(new Item(p, detail.getQuantity()));
        }
        double discount = 0;
        double total = 0;
        String couponCode = couponService.getCouponCodeByOrderId(orderId);
        if (couponCode == null) {
            couponCode = "------------------";
        }

        String applyCouponCode = order.getCouponCode();

        if (applyCouponCode != null) {
            discount = couponService.getDiscountAmount(applyCouponCode, order.getTotalAmount());
            total = order.getTotalAmount() - discount;
        }

        model.addAttribute("order", order);
        model.addAttribute("accountService", accountService);
        model.addAttribute("customer", customer);
        model.addAttribute("items", items);
        model.addAttribute("discount", discount);
        model.addAttribute("total", total);
        model.addAttribute("totalAmount", order.getTotalAmount());
        model.addAttribute("givenMoney", order.getGivenMoney());
        model.addAttribute("excessMoney", order.getExcessMoney());

        model.addAttribute("couponCode", couponCode);

        return "Customer/bill";
    }
}
