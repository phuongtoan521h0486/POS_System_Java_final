package com.thd.pos_system_java_final.middlewares;

import com.thd.pos_system_java_final.models.Account.AuthenticationParams;

public abstract class Middleware {
    protected Middleware next;

    public Middleware setNextChain(Middleware next) {
        this.next = next;
        return next;
    }

    public abstract String check(AuthenticationParams params);

    protected String checkNext(AuthenticationParams params) {
        if (next != null) {
            return next.check(params);
        }
        params.session.setAttribute("username", params.loginRequest.getUsername());
        params.session.setAttribute("account", params.accountService.getAccountByUsername(params.loginRequest.getUsername()));
        return "redirect:/";
    }
}
