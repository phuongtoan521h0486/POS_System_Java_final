package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.Account.AccountRole;
import com.thd.pos_system_java_final.models.DataMail;
import com.thd.pos_system_java_final.models.Account.Account;
import com.thd.pos_system_java_final.models.Order.Order;
import com.thd.pos_system_java_final.models.Order.OrderDetail;
import com.thd.pos_system_java_final.models.Order.OrderDetailRepository;
import com.thd.pos_system_java_final.models.Order.OrderRepository;
import com.thd.pos_system_java_final.services.*;
import com.thd.pos_system_java_final.ultils.JwtUtil;
import com.thd.pos_system_java_final.ultils.WebUtils;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    @Autowired
    private HttpSession session;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model,
                            @RequestParam(name = "startDate", required = false) String startDate,
                            @RequestParam(name = "endDate", required = false) String endDate) {
        String username =  session.getAttribute("username").toString();
        Account account = accountService.getAccountByUsername(username);
        model.addAttribute("utils", new WebUtils());

        model.addAttribute("account", account);
        model.addAttribute("imageUtils", imageService);

        model.addAttribute("accountService", accountService);
        model.addAttribute("customerService", customerService);

        List<Order> orders = new ArrayList<>();
        if (startDate != null && endDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/dd/yyyy");
            try {
                Date parsedStartDate = ( !startDate.isEmpty()) ? dateFormat.parse(startDate) : null;
                Date parsedEndDate = (!endDate.isEmpty()) ? dateFormat.parse(endDate) : null;

                orders = dashboardService.getOrdersByDateRange(parsedStartDate, parsedEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            // Today
            LocalDate today = LocalDate.now();
            Date fromDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date toDate = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            orders = dashboardService.getOrdersByDateRange(fromDate, toDate);

        }
        model.addAttribute("totalProduct", dashboardService.countProductsOfOrders(orders));
        model.addAttribute("orders", orders);
        model.addAttribute("revenue", dashboardService.getRevenue(orders));
        model.addAttribute("profit", dashboardService.getProfit(orders));

        return "Dashboard/dashboard";
    }

    @GetMapping("/add")
    public String add(HttpSession session, Model model) {
        model.addAttribute("utils", new WebUtils());
        model.addAttribute("account", session.getAttribute("account"));
        return "User/add";
    }

    @PostMapping("/add")
    public String add(Account account, RedirectAttributes redirectAttributes) {
        Account acc = accountService.getAccountByEmail(account.getEmail());
        if (acc == null) {
            accountService.createAccount(account);
            account.setPicture(getDefaultImageBytes());
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
        List<Account> accounts = accountService.getAccountByEmployeeRole();
        model.addAttribute("accounts", accounts);
        model.addAttribute("imageUtils", imageService);
        return "User/index";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model, ImageService imageService) {
        String username =  session.getAttribute("username").toString();
        Account account = accountService.getAccountByUsername(username);

        model.addAttribute("utils", new WebUtils());
        model.addAttribute("account", account);
        model.addAttribute("imageUtils", imageService);

        return "User/profile";
    }

    @PostMapping("/profile")
    public String profile(HttpSession session, Model model, String name, String email, String phone, MultipartFile pictureFile) throws IOException {
        String username =  session.getAttribute("username").toString();
        Account account = accountService.getAccountByUsername(username);

        account.setFullName(name);
        account.setEmail(email);
        account.setPhone(phone);
        if (!pictureFile.isEmpty()) {
            account.setPicture(pictureFile.getBytes());
        } else {
            account.setPicture(getDefaultImageBytes());
        }
        accountService.updateAccount(account);
        return "redirect:/user/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        accountService.deleteAccount(id);
        return "redirect:/user";
    }

    @PostMapping("/block/{id}")
    public String blockUser(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        account.setStatus(false);
        accountService.updateAccount(account);
        return "redirect:/user";
    }

    @PostMapping("/enable/{id}")
    public String enableUser(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        account.setStatus(true);
        accountService.updateAccount(account);
        return "redirect:/user";
    }

    @PostMapping("/resend/{id}")
    public String resendEmailForUser(@PathVariable int id) {
        Account account = accountService.getAccountById(id);

        resendEmail(account.getEmail());

        return "redirect:/user";
    }

    @PostMapping("/changePassword")
    public String changePassword(String oldPassword, String password) {
        String username = (String) session.getAttribute("username");
        Account account = accountService.getAccountByUsername(username);

        if (BCrypt.checkpw(oldPassword, account.getPassword())) {
            account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
            accountService.updateAccount(account);
        }
        session.invalidate(); // clear session
        return "Login";
    }

    @GetMapping("/{id}")
    public String userDetail(@PathVariable int id, Model model) {
        Account staff = accountService.getAccountById(id);
        List<Order> orders = orderRepository.findOrdersByAccountId(id);

        double sum = 0;
        for (Order order : orders) {
            sum += order.getTotalAmount();
        }


        model.addAttribute("imageUtils", imageService);
        model.addAttribute("staff", staff);
        model.addAttribute("totalAmount", sum);

        model.addAttribute("customerService", customerService);
        model.addAttribute("orders", orders);

        model.addAttribute("utils", new WebUtils());
        model.addAttribute("account", session.getAttribute("account"));
        return "User/detail";
    }

    private byte[] getDefaultImageBytes() {
        try {
            Resource resource = new ClassPathResource("static/images/avatar-default.png");
            return Files.readAllBytes(resource.getFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
