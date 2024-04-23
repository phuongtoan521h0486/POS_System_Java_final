package com.thd.pos_system_java_final.middlewares;

import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AuthenticationParams;
import com.thd.pos_system_java_final.models.Account.LoginRequest;
import com.thd.pos_system_java_final.services.AccountService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public class AccountPasswordMiddleware extends Middleware {
    @Override
    public String check(AuthenticationParams params) {
        Account account = params.accountService.getAccountByUsername(params.loginRequest.getUsername());
        String hashedPassword = account.getPassword();
        if (!BCrypt.checkpw(params.loginRequest.getPassword(), hashedPassword)) {
            params.model.addAttribute("error", "Your username or password is not correct");
            return "/Login";
        } else {
            return checkNext(params);
        }
    }
}
