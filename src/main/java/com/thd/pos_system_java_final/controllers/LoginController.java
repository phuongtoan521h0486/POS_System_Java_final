package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.middlewares.*;
import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Account.AccountRole;
import com.thd.pos_system_java_final.models.Account.AuthenticationParams;
import com.thd.pos_system_java_final.models.Account.LoginRequest;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.shared.ultils.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
@AllArgsConstructor
public class LoginController {
    @Autowired
    private JwtUtil jwt;
    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String login(HttpSession session) {
        if(accountService.getAccountByUsername("admin") == null) {
            Account admin = new Account();
            admin.setEmail("admin@gmail.com");
            admin.setUsername("admin");
            admin.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt(10)));
            admin.setActivate(true);
            accountService.createAccount(admin);
        }

        if (session.getAttribute("username") != null) {
            return "redirect:/";
        }
        return "Login";
    }
    @PostMapping("/login")
    public String login(LoginRequest loginRequest, Model model, HttpSession session)
    {
        AuthenticationParams params = new AuthenticationParams(loginRequest, model, session, accountService);
        Middleware authenticationChain = new AccountExistMiddleware();
        authenticationChain.setNextChain(new AccountPasswordMiddleware())
                .setNextChain(new AccountActivateMiddleware())
                .setNextChain(new AccountStatusMiddleware())
                .setNextChain(new AccountFirstLoginMiddleware());

        return authenticationChain.check(params);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam String token, HttpSession session) {
        try {
            if (!jwt.isTokenExpired(token)) {
                System.out.println("Hello login successfully");
                String username = jwt.extractUsername(token);
                Account account = accountService.getAccountByUsername(username);
                session.setAttribute("usernameWelcome", username);
                accountService.updateAccount(account);
                return "redirect:/welcome";
            }
            return "redirect:/login";
        } catch (JwtException e) {
            return "error";
        }
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session) {
        String username = (String) session.getAttribute("usernameWelcome");
        if (username == null) {
            return "redirect:/login";
        }
        return "welcome";
    }

    @PostMapping("/welcome") // not finish
    public String welcome(LoginRequest loginRequest, HttpSession session) {
        String username = (String) session.getAttribute("usernameWelcome");
        try {
            String password = String.valueOf(loginRequest.getPassword());
            String rePassword = String.valueOf(loginRequest.getRePassword());
            Account account = accountService.getAccountByUsername(username);
            String hashedPassword = account.getPassword();
            if (!password.equals(rePassword)) {
                return "redirect:/welcome";
            } else if (BCrypt.checkpw(password, hashedPassword)) {
                return "redirect:/welcome";
            }

            account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
            account.setActivate(true);
            accountService.updateAccount(account);
            session.invalidate();
            return "redirect:/login";
        } catch (Exception e) {
            return "error";
        }
    }

}
