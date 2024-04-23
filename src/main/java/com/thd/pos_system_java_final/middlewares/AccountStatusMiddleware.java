package com.thd.pos_system_java_final.middlewares;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AuthenticationParams;
import com.thd.pos_system_java_final.models.Account.LoginRequest;
import com.thd.pos_system_java_final.services.AccountService;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public class AccountStatusMiddleware extends Middleware {
    @Override
    public String check(AuthenticationParams params) {
        Account account = params.accountService.getAccountByUsername(params.loginRequest.getUsername());
        if (!account.isStatus()) {
            params.model.addAttribute("error", "Your account is blocked, please contact the admin");
            return "/Login";
        } else {
            return checkNext(params);
        }
    }
}
