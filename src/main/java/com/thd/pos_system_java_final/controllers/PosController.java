package com.thd.pos_system_java_final.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/pos")
@SessionAttributes("username")

public class PosController {
    @GetMapping("")
    public String index(Model model) {
        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/checkout")
    public String checkOut() {
        return "checkout";
    }
}
