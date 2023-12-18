package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.DataMail;
import com.thd.pos_system_java_final.models.account.Account;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    @Autowired
    AccountService accountService;

    @Autowired
    private MailService mailService;

    @GetMapping("")
    @ResponseBody
    public String index() {
        return "Admin Page";
    }

    @GetMapping("/register")
    public String register() {
        return "Admin/CreateAccount";
    }

    @PostMapping("/register")
    public String add(Account account, RedirectAttributes redirectAttributes) {
        Account acc = accountService.getAccountByEmail(account.getEmail());
        if (acc == null) {
            accountService.createAccount(account);
            sendEmail(account.getEmail());
            return "redirect:/admin";
        } else {
            redirectAttributes.addFlashAttribute("error", "email " + account.getEmail() + " is already exist"); // truyen attr zo view
            return "redirect:/admin/register";
        }
    }

    private boolean sendEmail(String email) {
        String defaultUsername = email.split("@")[0];
        try {
            DataMail dataMail = new DataMail();

            dataMail.setTo(email);
            dataMail.setSubject("Tieu de cua email");

            Map<String, Object> props = new HashMap<>();
            props.put("username", defaultUsername);
            props.put("link", "abcdef");
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, "emailTemplate");
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return  false;
    }

    @GetMapping("/add-product")
    public String addProduct() {
        return "adminAddEdit";
    }

    @GetMapping("/order")
    public String order() {
        return "adminOrder";
    }

    @GetMapping("/showstaff")
    public String showStaff() {
        return "adminStaff";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
