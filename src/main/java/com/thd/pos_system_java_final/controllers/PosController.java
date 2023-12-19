package com.thd.pos_system_java_final.controllers;


import com.thd.pos_system_java_final.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/pos")

public class PosController {
    @Autowired
    AccountService accountService;

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", accountService.getAccountByUsername(username));
        return "mainhomepage";
    }


    @GetMapping("/checkout")
    public String checkOut() {
        return "checkout";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
