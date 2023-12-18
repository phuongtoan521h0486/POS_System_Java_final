package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.account.Account;
import com.thd.pos_system_java_final.models.account.LoginRequest;
import com.thd.pos_system_java_final.services.AccountService;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
@AllArgsConstructor
@SessionAttributes("username")
public class LoginController {
    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public String login(Model model) {
        if (model.getAttribute("username") != null) {
            return "redirect:/pos";
        }
        return "Login";
    }
    @PostMapping("/login")
    public String login(LoginRequest loginRequest, Model model)
    {
        //Account account = accountService.getAccountByUsername(loginRequest.getUsername());
        try {
            Account account = accountService.getAccountByUsername(loginRequest.getUsername());
            if (account == null) {
                model.addAttribute("error", "Your account is not exist, please contact to admin"); // truyen attr zo view
                return "/Login";
            } else {
                String hashedPassword = account.getPassword();
                if (!BCrypt.checkpw(loginRequest.getPassword(), hashedPassword)) {
                    model.addAttribute("error", "Your username or password is not correct"); // truyen attr zo view
                    return "/Login";
                } else if (!account.isActivate()) {
                    model.addAttribute("error", "Please login through out email"); // truyen attr zo view
                    return "/Login";
                } else if (!account.isStatus()) {
                    model.addAttribute("error", "Your account is blocked, please contact Admin"); // truyen attr zo view
                    return "/Login";
                }
            }
            System.out.println(loginRequest.getUsername());
            model.addAttribute("username", loginRequest.getUsername()); // truyen attr zo view
            return "redirect:/pos";
        }catch (Exception e) {
            return "Login failed: " + e.getMessage();
        }
    }

}
