package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.DataMail;
import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.services.AccountService;
import com.thd.pos_system_java_final.services.ImageService;
import com.thd.pos_system_java_final.services.MailService;
import com.thd.pos_system_java_final.ultils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MailService mailService;
    @Autowired
    private JwtUtil jwt;
    @Autowired
    private ImageService imageService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("username") == null) {
            return "Login";
        }
        String username =  session.getAttribute("username").toString();
        Account account = accountService.getAccountByUsername(username);

        model.addAttribute("account", account);
        model.addAttribute("imageUtils", imageService);
        return "Dashboard/dashboard";
    }

    @GetMapping("/add")
    public String add() {
        return "User/add";
    }

    @PostMapping("/add")
    public String add(Account account, RedirectAttributes redirectAttributes) {
        Account acc = accountService.getAccountByEmail(account.getEmail());
        if (acc == null) {
            accountService.createAccount(account);
            sendEmail(account.getEmail());
            return "redirect:/user";
        } else {
            redirectAttributes.addFlashAttribute("error", "email " + account.getEmail() + " is already exist");
            return "redirect:/user/add";
        }
    }

    private boolean sendEmail(String email) {
        String defaultUsername = email.split("@")[0];
        try {
            DataMail dataMail = new DataMail();

            dataMail.setTo(email);
            dataMail.setSubject("Invitation to POS system");

            Map<String, Object> props = new HashMap<>();
            props.put("username", defaultUsername);

            String token = jwt.createToken(defaultUsername);
            String confirmationLink = "http://localhost:8888/confirm?token=" + token;
            props.put("link", confirmationLink);
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, "Email/emailCreate");
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return  false;
    }

    private boolean resendEmail(String email) {
        String defaultUsername = email.split("@")[0];
        try {
            DataMail dataMail = new DataMail();

            dataMail.setTo(email);
            dataMail.setSubject("Resend email to POS system");

            Map<String, Object> props = new HashMap<>();
            props.put("username", defaultUsername);

            String token = jwt.createToken(defaultUsername);
            String confirmationLink = "http://localhost:8888/confirm?token=" + token;
            props.put("link", confirmationLink);
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, "Email/emailResend");
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return  false;
    }

    @GetMapping("")
    public String showStaff(Model model, ImageService imageService) {
        List<Account> accounts = accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        model.addAttribute("imageUtils", imageService);
        return "User/index";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model, ImageService imageService) {
        if (session.getAttribute("username") == null) {
            return "Login";
        }
        String username =  session.getAttribute("username").toString();
        Account account = accountService.getAccountByUsername(username);

        model.addAttribute("account", account);
        model.addAttribute("imageUtils", imageService);

        return "User/profile";
    }

    @PostMapping("/profile")
    public String profile(HttpSession session, Model model, String name, String email, String phone, MultipartFile pictureFile) throws IOException {
        if (session.getAttribute("username") == null) {
            return "Login";
        }
        String username =  session.getAttribute("username").toString();
        Account account = accountService.getAccountByUsername(username);

        account.setFullName(name);
        account.setEmail(email);
        account.setPhone(phone);
        if (!pictureFile.isEmpty()) {
            account.setPicture(pictureFile.getBytes());
        }
        accountService.updateAccount(account);

        return "Dashboard/dashboard";
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<Account>> listUser() {
        // Fetch the updated accounts
        List<Account> accounts = accountService.getAllAccount();
        return ResponseEntity.ok(accounts);
    } // de test

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        System.out.println(id);
        accountService.deleteAccount(id);
        return "redirect:/user";
    }

    @PostMapping("/update/{id}")
    public void blockUser(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        accountService.updateAccount(account);
    }// test

}