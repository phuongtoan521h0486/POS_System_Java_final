package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class DashboardController {
    @Autowired
    private OrderRepository orderRepository;
    @PostMapping("/dashboard/getDataBarChart")
    public List<Double> getDataBarChart(@RequestParam String startDate, @RequestParam String endDate) throws ParseException, UnsupportedEncodingException {
        if (startDate == null && endDate == null) {
            startDate = endDate = new Date().toString();
        }


        String decodedStartDate = URLDecoder.decode(startDate, "UTF-8");
        String decodedEndDate = URLDecoder.decode(endDate, "UTF-8");


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        Date from = dateFormat.parse(decodedStartDate);
        Date to = dateFormat.parse(decodedEndDate);

        if (from.equals(to)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(to);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            to = calendar.getTime();
        }

        System.out.println(from+", "+to);

        List<Order> orders = orderRepository.findByOrderDateBetween(from, to);
        Map<String, Double> dailyRevenues = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> dateFormat.format(order.getOrderDate()),
                        Collectors.summingDouble(Order::getTotalAmount)
                ));

        List<Double> revenuesPerDay = new ArrayList<>(dailyRevenues.values());
        return revenuesPerDay;
    }

}
