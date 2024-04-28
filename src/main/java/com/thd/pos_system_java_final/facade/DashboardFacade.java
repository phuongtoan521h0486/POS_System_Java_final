package com.thd.pos_system_java_final.facade;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.DTO.DashboardData;
import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.CustomerService;
import com.thd.pos_system_java_final.services.DashboardService;
import com.thd.pos_system_java_final.services.ImageService;
import com.thd.pos_system_java_final.shared.ultils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DashboardFacade { // Apply Facade pattern
    @Autowired
    private AccountService accountService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WebUtils webUtils;

    @Autowired
    private OrderRepository orderRepository;

    private static List<String> getAllDatesBetween(Date from, Date to) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        List<String> allDates = new ArrayList<>();

        // Convert Date objects to LocalDate
        LocalDate currentLocalDate = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toLocalDate = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        while (!currentLocalDate.isAfter(toLocalDate)) {
            allDates.add(dateFormat.format(Date.from(currentLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
            currentLocalDate = currentLocalDate.plusDays(1);
        }
        return allDates;
    }

    public DashboardData getDashboardData(String username, String startDate, String endDate) {
        Account account = accountService.getAccountByUsername(username);
        List<Order> orders = getOrders(startDate, endDate);
        int totalProduct = dashboardService.countProductsOfOrders(orders);
        double revenue = dashboardService.getRevenue(orders);
        double profit = dashboardService.getProfit(orders);

        return new DashboardData(account, orders, totalProduct, revenue, profit,
                imageService, webUtils, customerService, accountService);
    }

    private List<Order> getOrders(String startDate, String endDate) {
        List<Order> orders = new ArrayList<>();
        if (startDate != null && endDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/dd/yyyy");
            try {
                Date parsedStartDate = (!startDate.isEmpty()) ? dateFormat.parse(startDate) : null;
                Date parsedEndDate = (!endDate.isEmpty()) ? dateFormat.parse(endDate) : null;

                orders = dashboardService.getOrdersByDateRange(parsedStartDate, parsedEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            // Today
            LocalDate today = LocalDate.now();
            Date fromDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date toDate = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            orders = dashboardService.getOrdersByDateRange(fromDate, toDate);
        }
        return orders;
    }

    public List<Double> getRevenuesPerDay(String startDate, String endDate) throws ParseException, UnsupportedEncodingException {
        if (startDate == null && endDate == null) {
            startDate = endDate = new Date().toString();
        }
        String decodedStartDate = URLDecoder.decode(startDate, StandardCharsets.UTF_8);
        String decodedEndDate = URLDecoder.decode(endDate, StandardCharsets.UTF_8);


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

        return allDates.stream()
                .map(date -> dailyRevenues.getOrDefault(date, 0.0))
                .collect(Collectors.toList());
    }
}
