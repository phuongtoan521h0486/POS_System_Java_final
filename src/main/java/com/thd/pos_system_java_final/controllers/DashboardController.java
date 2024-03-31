package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.facade.DashboardFacade;
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


        List<Order> orders = orderRepository.findByOrderDateBetween(from, to);
        Map<String, Double> dailyRevenues = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> dateFormat.format(order.getOrderDate()),
                        Collectors.summingDouble(Order::getTotalAmount)
                ));

        List<String> allDates = getAllDatesBetween(from, to);

        List<Double> revenuesPerDay = allDates.stream()
                .map(date -> dailyRevenues.getOrDefault(date, 0.0))
                .collect(Collectors.toList());

        revenuesPerDay.forEach(System.out::println);

        return revenuesPerDay;
    }
    private static List<String> getAllDatesBetween(Date from, Date to) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        List<String> allDates = new ArrayList<>();

        // Convert Date objects to LocalDate
        LocalDate currentLocalDate = from.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate toLocalDate = to.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        while (!currentLocalDate.isAfter(toLocalDate)) {
            allDates.add(dateFormat.format(Date.from(currentLocalDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant())));
            currentLocalDate = currentLocalDate.plusDays(1);
        }
        return allDates;
    }

}
