package com.thd.pos_system_java_final.models.DTO;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.CustomerService;
import com.thd.pos_system_java_final.services.ImageService;
import com.thd.pos_system_java_final.shared.ultils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DashboardData {
    public Account account;
    public List<Order> orders;
    public int totalProduct;
    public double revenue;
    public double profit;
    public ImageService imageService;
    public WebUtils webUtils;
    public CustomerService customerService;
    public AccountService accountService;
}
