package com.thd.pos_system_java_final.middlewares;

import com.thd.pos_system_java_final.models.Account.AuthenticationParams;

public class AccountFirstLoginMiddleware extends Middleware {
    @Override
    public String check(AuthenticationParams params) {
        if (params.loginRequest.getPassword().equals(params.loginRequest.getUsername())) {
            params.session.setAttribute("usernameWelcome", params.loginRequest.getUsername());
            return "redirect:/welcome";
        } else {
            return checkNext(params);
        }
    }
}