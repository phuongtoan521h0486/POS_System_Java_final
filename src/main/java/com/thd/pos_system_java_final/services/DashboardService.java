package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class DashboardService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductRepository productRepository;


    public List<Order> getOrdersByDateRange(Date fromDate, Date toDate) {
        if (isToday(fromDate, toDate)) {
            return getOrdersToday();
        } else if (isYesterday(fromDate, toDate)) {
            return getOrdersYesterday();
        } else if (isLast7Days(fromDate, toDate)) {
            return getOrdersLast7Days();
        } else if (isThisMonth(fromDate, toDate)) {
            return getOrdersThisMonth();
        } else {
            return orderRepository.findByOrderDateBetween(fromDate, toDate);
        }
    }

    private boolean isToday(Date fromDate, Date toDate) {
        return fromDate.equals(toDate);
    }

    private boolean isLast7Days(Date fromDate, Date toDate) {
        long timeDifference = toDate.getTime() - fromDate.getTime();

        // Convert milliseconds to days
        long daysDifference = timeDifference / (24 * 60 * 60 * 1000);

        // Check if the difference is exactly 7 days
        return daysDifference == 6;
    }

    private boolean isYesterday(Date fromDate, Date toDate) {
        LocalDate fromLocalDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toLocalDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return toLocalDate.isEqual(fromLocalDate.plusDays(1));
    }

    private boolean isThisMonth(Date fromDate, Date toDate) {
        LocalDate fromLocalDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toLocalDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        boolean sameMonth = fromLocalDate.getMonth().equals(toLocalDate.getMonth());

        boolean isFirstDay = fromLocalDate.getDayOfMonth() == 1;

        boolean isLastDay = toLocalDate.getDayOfMonth() == toLocalDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        return sameMonth && isFirstDay && isLastDay;
    }

    private List<Order> getOrdersToday() {
        Date todayStart = removeTimePart(new Date());

        Date todayEnd = addOneDay(todayStart);

        return orderRepository.findByOrderDateBetween(todayStart, todayEnd);
    }

    private List<Order> getOrdersLast7Days() {
        Date todayEnd = addOneDay(removeTimePart(new Date()));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date last7DaysStart = removeTimePart(calendar.getTime());

        return orderRepository.findByOrderDateBetween(last7DaysStart, todayEnd);
    }

    private List<Order> getOrdersYesterday() {
        Date todayEnd = addOneDay(removeTimePart(new Date()));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterdayStart = removeTimePart(calendar.getTime());

        return orderRepository.findByOrderDateBetween(yesterdayStart, todayEnd);
    }

    private List<Order> getOrdersThisMonth() {
        Date todayEnd = addOneDay(removeTimePart(new Date()));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date thisMonthStart = removeTimePart(calendar.getTime());

        return orderRepository.findByOrderDateBetween(thisMonthStart, todayEnd);
    }

    private Date removeTimePart(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date addOneDay(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
    private Date minusOneDay(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public String getTitle(Date fromDate, Date toDate) {
        if (isToday(fromDate, toDate)) {
            return "Today";
        } else if (isYesterday(fromDate, toDate)) {
            return "Yesterday";
        } else if (isLast7Days(fromDate, toDate)) {
            return "Last 7 Days";
        } else if (isThisMonth(fromDate, toDate)) {
            return "This Month";
        } else {
            return "Custom";
        }
    }

}