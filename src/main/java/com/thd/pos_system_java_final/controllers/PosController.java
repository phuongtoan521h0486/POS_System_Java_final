package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.DataMail;
import com.thd.pos_system_java_final.services.EmailSenderService;
import com.thd.pos_system_java_final.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pos")
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
