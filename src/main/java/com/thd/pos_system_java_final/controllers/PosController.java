package com.thd.pos_system_java_final.controllers;


import com.thd.pos_system_java_final.models.Product.Product;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")

public class PosController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        List<Product> productList = new ArrayList<>(productRepository.findAll());
        model.addAttribute("imageUtils", imageService);
        model.addAttribute("products", productList);
        model.addAttribute("user", accountService.getAccountByUsername(username));
        return "index";
    }


    @GetMapping("/checkout")
    public String checkOut() {
        return "POS/checkout";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
