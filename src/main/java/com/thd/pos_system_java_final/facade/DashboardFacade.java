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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
