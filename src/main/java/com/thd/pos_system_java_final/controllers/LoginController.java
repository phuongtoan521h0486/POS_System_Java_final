package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.account.Account;
import com.thd.pos_system_java_final.models.account.LoginRequest;
import com.thd.pos_system_java_final.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
@AllArgsConstructor
public class LoginController {
    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public String login() {
        return "Login";
    }
    @PostMapping("/login")
    public String login(LoginRequest loginRequest, RedirectAttributes redirectAttributes)
    {
        //Account account = accountService.getAccountByUsername(loginRequest.getUsername());
        try {
            redirectAttributes.addFlashAttribute("username", loginRequest.getUsername()); // truyen attr zo view
            return "redirect:/pos";
        }catch (Exception e) {
            return "Login failed: " + e.getMessage();
        }
    }
}
