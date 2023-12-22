package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Customer.Customer;
import com.thd.pos_system_java_final.models.Customer.CustomerRepository;
import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderDetail;
import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public int calculateTotalOrder(int customerId) {
        return orderRepository.countAllByCustomerId(customerId);
    }

    public double calculateTotalSpend(int customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        double total = 0;
        for(Order order: orders) {
            total += order.getTotalAmount();
        }
        return  total;
    }

    public Order getLatestOrder() {
        return orderRepository.findFirstByOrderByOrderDateDesc();
    }

    public int calculateTotalQuantity(int customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        int total = 0;
        for(Order order: orders) {
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrOrderId(order.getOrderId());
            for(OrderDetail detail: orderDetails) {
                total += detail.getQuantity();
            }
        }
        return  total;
    }

}
