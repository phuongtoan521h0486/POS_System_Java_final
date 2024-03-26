package com.thd.pos_system_java_final.shared.ultils;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AccountRole;
import com.thd.pos_system_java_final.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RoleInterceptor implements HandlerInterceptor {
    @Autowired
    AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String username = (String) request.getSession().getAttribute("username");
        Account account = accountService.getAccountByUsername(username);
        if (account != null && account.getRole() == AccountRole.ADMIN) {
            return true;
        } else {
            response.sendRedirect("/error");
            return false;
        }
    }
}
