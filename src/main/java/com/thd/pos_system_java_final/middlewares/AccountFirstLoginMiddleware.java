package com.thd.pos_system_java_final.middlewares;

import com.thd.pos_system_java_final.models.Account.LoginRequest;
import com.thd.pos_system_java_final.services.AccountService;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public class AccountFirstLoginMiddleware extends Middleware {
    @Override
    public String check(LoginRequest loginRequest, Model model, HttpSession session, AccountService accountService) {
        if (loginRequest.getPassword().equals(loginRequest.getUsername())) {
            session.setAttribute("usernameWelcome", loginRequest.getUsername());
            return "redirect:/welcome";
        } else {
            return checkNext(loginRequest, model, session, accountService);
        }
    }
}