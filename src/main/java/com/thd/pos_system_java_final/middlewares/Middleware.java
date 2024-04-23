package com.thd.pos_system_java_final.middlewares;

import com.thd.pos_system_java_final.models.Account.LoginRequest;
import com.thd.pos_system_java_final.services.AccountService;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public abstract class Middleware {
    protected Middleware next;

    public Middleware setNextChain(Middleware next) {
        this.next = next;
        return next;
    }

    public abstract String check(LoginRequest loginRequest, Model model, HttpSession session, AccountService accountService);

    protected String checkNext(LoginRequest loginRequest, Model model, HttpSession session, AccountService accountService) {
        if (next != null) {
            return next.check(loginRequest, model, session, accountService);
        }
        session.setAttribute("username", loginRequest.getUsername());
        session.setAttribute("account", accountService.getAccountByUsername(loginRequest.getUsername()));
        return "redirect:/";
    }
}
