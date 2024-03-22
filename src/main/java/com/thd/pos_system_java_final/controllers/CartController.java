package com.thd.pos_system_java_final.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Cart.Cart;
import com.thd.pos_system_java_final.models.Cart.Item;
import com.thd.pos_system_java_final.models.Customer.Customer;
import com.thd.pos_system_java_final.models.Customer.CustomerRepository;
import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderDetail;
import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import com.thd.pos_system_java_final.models.Product.Product;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.CartService;
import com.thd.pos_system_java_final.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CartService cartService;
    @Autowired
    private HttpSession session;
    @GetMapping("/step-1")
    public String step1() {
        return "POS/step1";
    }

    @PostMapping("/step-1")
    public String step1(String phone, Model model) {
        Customer customer = customerRepository.findByPhoneNumber(phone);
        session.setAttribute("customer", customer);
        model.addAttribute("myCart", (List<Item>) session.getAttribute("cart"));
        model.addAttribute("imageUtils", imageService);
        model.addAttribute("cartService", cartService);
        return "POS/step2";
    }

    @GetMapping("/step-2")
    public String step2(Model model) {
        model.addAttribute("myCart", (List<Item>) session.getAttribute("cart"));
        model.addAttribute("imageUtils", imageService);
        model.addAttribute("cartService", cartService);
        return "POS/step2";
    }

    @PostMapping("/step-2")
    public String step2(double givenMoney, Model model) {
        // Order
        Order order = new Order();
        order.setOrderDate(new Date());

        List<Item> items = cartService.getCartItems();
        double totalAmount = cartService.calculateTotalAmount();

        order.setTotalAmount(totalAmount);
        order.setGivenMoney(givenMoney);
        order.setExcessMoney(givenMoney - totalAmount);
        order.setQuantity(cartService.calculateTotalQuantity());

        Customer customer = (Customer) session.getAttribute("customer");
        order.setCustomerId(customer.getCustomerId());

        String username = (String) session.getAttribute("username");
        Account account = accountService.getAccountByUsername(username);
        order.setAccountId(account.getAccountId());

        orderRepository.save(order);

        int orderId = order.getOrderId();

        for (Item item: items) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(item.getProduct().getProductId());
            orderDetail.setQuantity(item.getQuantity());

            orderDetailRepository.save(orderDetail);
        }

        model.addAttribute("order", order);
        model.addAttribute("salespeople", username);
        model.addAttribute("customer", customer);
        model.addAttribute("items", items);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("givenMoney", givenMoney);
        model.addAttribute("excessMoney", givenMoney - totalAmount);

        return "POS/step3";
    }

    @GetMapping("/step-3")
    public String step3() {
        cartService.resetCart();
        session.removeAttribute("cart");
        return "redirect:/";
    }

    @GetMapping("/complete")
    public String complete(Model model) {

        Order order = new Order();
        order.setOrderDate(new Date());

        List<Item> items = cartService.getCartItems();
        double totalAmount = cartService.calculateTotalAmount();
        double givenMoney = totalAmount;
        order.setTotalAmount(totalAmount);
        order.setGivenMoney(givenMoney);
        order.setExcessMoney(givenMoney - totalAmount);
        order.setQuantity(cartService.calculateTotalQuantity());

        Customer customer = (Customer) session.getAttribute("customer");
        order.setCustomerId(customer.getCustomerId());

        String username = (String) session.getAttribute("username");
        Account account = accountService.getAccountByUsername(username);
        order.setAccountId(account.getAccountId());

        orderRepository.save(order);

        int orderId = order.getOrderId();

        for (Item item: items) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(item.getProduct().getProductId());
            orderDetail.setQuantity(item.getQuantity());

            orderDetailRepository.save(orderDetail);
        }

        model.addAttribute("order", order);
        model.addAttribute("salespeople", username);
        model.addAttribute("customer", customer);
        model.addAttribute("items", items);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("givenMoney", givenMoney);
        model.addAttribute("excessMoney", givenMoney - totalAmount);

        return "POS/step3";
    }

    @PostMapping("/resetCart")
    public ResponseEntity<String> resetCart() {
        cartService.resetCart();
        session.removeAttribute("cart");

        return ResponseEntity.ok("Cart reset successfully");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Item item) {
        cartService.addItemToCart(item);
        session.setAttribute("cart", cartService.getCartItems());
        cartService.printCart();
        return ResponseEntity.ok("Product added to cart successfully");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeItemFromCart(@RequestBody int productId) {
        System.out.println(productId);
        try {
            cartService.removeItemFromCart(productId);

            return ResponseEntity.ok("Item removed from cart successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Item not found in cart");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing item from cart: " + e.getMessage());
        }
    }


    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getCartInfo() {
        Map<String, Object> cartInfo = new HashMap<>();
        cartInfo.put("totalAmount", cartService.calculateTotalAmount());
        cartInfo.put("totalQuantity", cartService.calculateTotalQuantity());
        return ResponseEntity.ok(cartInfo);
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestBody Map<String, Object> payload) {
        int productId = (int) payload.get("productId");
        int newQuantity = (int) payload.get("quantity");

        try {
            cartService.updateCartItem(productId, newQuantity);
            return ResponseEntity.ok("Cart item updated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Item not found in cart");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating cart item: " + e.getMessage());
        }
    }

    @GetMapping("/getCartQuantity")
    public ResponseEntity<Integer> getCartQuantity() {
        return ResponseEntity.ok(cartService.getCartItems().size());
    }
}

