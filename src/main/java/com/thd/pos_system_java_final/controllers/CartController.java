package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Cart.Item;
import com.thd.pos_system_java_final.models.Coupon.Coupon;
import com.thd.pos_system_java_final.models.Coupon.CouponType;
import com.thd.pos_system_java_final.models.Customer.Customer;
import com.thd.pos_system_java_final.models.Customer.CustomerRepository;
import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderDetail;
import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import com.thd.pos_system_java_final.payments.IPayment;
import com.thd.pos_system_java_final.payments.PaymentMethod;
import com.thd.pos_system_java_final.payments.PaymentParams;
import com.thd.pos_system_java_final.payments.SimplePaymentFactory;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.CartService;
import com.thd.pos_system_java_final.services.CouponService;
import com.thd.pos_system_java_final.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CustomerRepository customerRepository;
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
    @Autowired
    private CouponService couponService;

    @GetMapping("/step-1")
    public String step1() {
        return "POS/step1";
    }

    @PostMapping("/step-1")
    public String step1(String phone, Model model) {
        Customer customer = customerRepository.findByPhoneNumber(phone);
        session.setAttribute("customer", customer);
        model.addAttribute("myCart", session.getAttribute("cart"));
        model.addAttribute("imageUtils", imageService);
        model.addAttribute("cartService", cartService);
        return "POS/step2";
    }

    @GetMapping("/step-2")
    public String step2(Model model) {
        model.addAttribute("myCart", session.getAttribute("cart"));
        model.addAttribute("imageUtils", imageService);
        model.addAttribute("cartService", cartService);
        return "POS/step2";
    }

    @PostMapping("/step-2")
    public String step2(HttpServletRequest req, double givenMoney, Model model, PaymentMethod paymentMethod, String couponCode) {
        if (paymentMethod == null) {
            paymentMethod = PaymentMethod.Cash;
        }
        double totalAmount = cartService.calculateTotalAmount();
        PaymentParams params = new PaymentParams();
        params.setHttpServletRequest(req);
        params.setTotalAmount(totalAmount);
        params.setGivenMoney(givenMoney);

        IPayment payment = new SimplePaymentFactory().createPayment(paymentMethod);
        String paymentUrl = null;
        try {
            paymentUrl = payment.processPayment(params);
            saveTransaction(model, params, couponCode);
            return paymentUrl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveTransaction(Model model, PaymentParams params, String applyCode) {
        double totalAmount = params.getTotalAmount();
        double givenMoney = params.getGivenMoney();

        Order order = new Order();
        order.setOrderDate(new Date());

        List<Item> items = cartService.getCartItems();

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

        for (Item item : items) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(item.getProduct().getProductId());
            orderDetail.setQuantity(item.getQuantity());

            orderDetailRepository.save(orderDetail);
        }

        if (totalAmount > 999) {
            couponService.addCoupon(CouponType.PERCENTAGE, 5, order);
        } else {
            couponService.addCoupon(CouponType.FIXED_AMOUNT, 50, order);
        }

        String couponCode = couponService.getCouponCodeByOrderId(orderId);

        double discount = 0;
        if (applyCode != null) {
            System.out.println(applyCode);
            Coupon applyCoupon = couponService.getCouponByCode(applyCode);
            if (applyCoupon != null) {
                applyCoupon.setActive(true);
                discount = applyCoupon.getAmountDiscount(totalAmount);
                order.setCouponCode(applyCoupon.getCode());
                order.setExcessMoney(givenMoney - totalAmount + discount);
                orderRepository.save(order);
            }
        }
        double total = totalAmount - discount;

        model.addAttribute("order", order);
        model.addAttribute("salespeople", username);
        model.addAttribute("customer", customer);
        model.addAttribute("items", items);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("discount", discount);
        model.addAttribute("total", total);
        model.addAttribute("givenMoney", givenMoney);
        model.addAttribute("excessMoney", givenMoney - total);

        model.addAttribute("couponCode", couponCode);
    }


    @GetMapping("/step-3")
    public String step3() {
        cartService.resetCart();
        session.removeAttribute("cart");
        return "redirect:/";
    }

    @GetMapping("/complete")
    public String complete(Model model, HttpServletRequest req, String couponCode) {
        double totalAmount = cartService.calculateTotalAmount();
        double givenMoney = totalAmount;
        PaymentParams params = new PaymentParams();
        params.setHttpServletRequest(req);
        params.setTotalAmount(totalAmount);
        params.setGivenMoney(givenMoney);

        saveTransaction(model, params, couponCode);

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

