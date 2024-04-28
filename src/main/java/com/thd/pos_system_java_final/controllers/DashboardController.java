package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.facade.DashboardFacade;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

@RestController
public class DashboardController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DashboardFacade dashboardFacade;

    @PostMapping("/dashboard/getDataBarChart")
    public List<Double> getDataBarChart(@RequestParam String startDate, @RequestParam String endDate) throws ParseException, UnsupportedEncodingException {
        return dashboardFacade.getRevenuesPerDay(startDate, endDate);
    }


}
