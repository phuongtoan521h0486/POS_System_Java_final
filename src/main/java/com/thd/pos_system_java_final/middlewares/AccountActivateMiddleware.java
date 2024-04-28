package com.thd.pos_system_java_final.middlewares;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AuthenticationParams;

public class AccountActivateMiddleware extends Middleware {
    @Override
    public String check(AuthenticationParams params) {
        Account account = params.accountService.getAccountByUsername(params.loginRequest.getUsername());
        if (!account.isActivate()) {
            params.model.addAttribute("error", "Please activate your account via email");
            return "/Login";
        } else {
            return checkNext(params);
        }
    }
}

