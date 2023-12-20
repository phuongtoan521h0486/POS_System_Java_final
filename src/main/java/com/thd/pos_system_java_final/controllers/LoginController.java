package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.account.Account;
import com.thd.pos_system_java_final.models.account.LoginRequest;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.ultils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String login(Model model) {
        if (model.getAttribute("username") != null) {
            return "redirect:/";
        }
        return "Login";
    }
    @PostMapping("/login")
    public String login(LoginRequest loginRequest, Model model, HttpSession session)
    {
        try {
            Account account = accountService.getAccountByUsername(loginRequest.getUsername());
            if (account == null) {
                model.addAttribute("error", "Your account is not exist, please contact to admin");
                return "/Login";
            } else {
                String hashedPassword = account.getPassword();
                if (!BCrypt.checkpw(loginRequest.getPassword(), hashedPassword)) {
                    model.addAttribute("error", "Your username or password is not correct");
                    return "/Login";
                } else if (!account.isActivate()) {
                    model.addAttribute("error", "Please login through out email");
                    return "/Login";
                } else if (!account.isStatus()) {
                    model.addAttribute("error", "Your account is blocked, please contact Admin");
                    return "/Login";
                }
            }
            session.setAttribute("username", loginRequest.getUsername());
            return "redirect:/";
        }catch (Exception e) {
            return "Login failed: " + e.getMessage();
        }
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam String token, HttpSession session) {
        try {
            if (!jwt.isTokenExpired(token)) {
                System.out.println("Hello login successfully");
                String username = jwt.extractUsername(token);
                Account account = accountService.getAccountByUsername(username);
                account.setActivate(true);
                accountService.updateAccount(account);
                session.setAttribute("username", username);
                return "redirect:/";
            }
            return "redirect:/login";
        } catch (JwtException e) {
            return "redirect:/login";
        }
    }

}
