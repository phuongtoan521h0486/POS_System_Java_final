package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderDetail;
import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class DashboardService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductRepository productRepository;


    public List<Order> getOrdersByDateRange(Date fromDate, Date toDate) {
        LocalDate today = LocalDate.now();
        Date from, to = null;
        if (isToday(fromDate, toDate)) {
            from = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
            to = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else if (isYesterday(fromDate, toDate)) {
            to = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
            from = Date.from(today.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else if (isLast7Days(fromDate, toDate)) {
            to = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
            from = Date.from(today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else if (isThisMonth(fromDate, toDate)) {
            LocalDate firstDayOfMonth = today.withDayOfMonth(1);
            from = Date.from(firstDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
            LocalDate firstDayOfNextMonth = today.plusMonths(1).withDayOfMonth(1);
            to = Date.from(firstDayOfNextMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return orderRepository.findByOrderDateBetween(fromDate, toDate);
        }
        return orderRepository.findByOrderDateBetween(from, to);
    }

    private boolean isToday(Date fromDate, Date toDate) {
        Date today = removeTime(new Date());
        return fromDate.equals(today) && toDate.equals(today);
    }

    private boolean isYesterday(Date fromDate, Date toDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1); // Going back one day to get yesterday

        Date yesterday = removeTime(calendar.getTime());

        return fromDate.equals(yesterday) && toDate.equals(yesterday);
    }

    private boolean isLast7Days(Date fromDate, Date toDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -6); // Going back 6 days to get the start of the last 7 days

        Date last7DaysStart = removeTime(calendar.getTime());

        return fromDate.equals(last7DaysStart) && toDate.equals(removeTime(new Date()));
    }

    private boolean isThisMonth(Date fromDate, Date toDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Setting the day to the first day of the month

        Date thisMonthStart = removeTime(calendar.getTime());

        calendar.add(Calendar.MONTH, 1); // Moving to the first day of the next month
        calendar.add(Calendar.DAY_OF_YEAR, -1); // Going back one day to get the last day of the current month

        Date thisMonthEnd = removeTime(calendar.getTime());

        return fromDate.equals(thisMonthStart) && toDate.equals(thisMonthEnd);
    }

    private Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public double getProfitOfAnOrder(Order order) {
        double revenue = 0;
        List<OrderDetail> details = orderDetailRepository.findAllByOrderId(order.getOrderId());
        double totalImportPrice = 0;
        for(OrderDetail detail: details) {
            if (productRepository.findByProductId(detail.getProductId()) != null) {
                totalImportPrice += detail.getQuantity() * productRepository.findByProductId(detail.getProductId()).getImportPrice();
            }
        }
        return revenue = order.getTotalAmount() - totalImportPrice;
    }

    public List<Double> getProfitOfOrders(List<Order> orders) {
        List<Double>  revenues = new ArrayList<>();
        for(Order order: orders) {
            revenues.add(getProfitOfAnOrder(order));
        }
        return  revenues;
    }

    public double getProfit(List<Order> orders) {
        List<Double>  revenues = getProfitOfOrders(orders);
        double total = 0;
        for(Double revenue: revenues) {
            total += revenue;
        }
        return total;
    }

    public int countProductsOfOrders(List<Order> orders) {
        int count = 0;
        for (Order order: orders) {
            List<OrderDetail> details = orderDetailRepository.findAllByOrderId(order.getOrderId());
            for (OrderDetail detail: details) {
                count += detail.getQuantity();
            }
        }
        return count;
    }

    public double getRevenue(List<Order> orders) {
        double revenue = 0;
        for (Order order: orders) {
            revenue += order.getTotalAmount();
        }
        return revenue;
    }
}