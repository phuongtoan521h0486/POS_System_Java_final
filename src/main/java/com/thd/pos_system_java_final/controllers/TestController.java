package com.thd.pos_system_java_final.controllers;

import com.thd.pos_system_java_final.models.DataMail;
import com.thd.pos_system_java_final.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Controller()
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
    @Autowired
    private MailService mailService;

    @GetMapping("/email")
    @ResponseBody
    public String email() {
        sendEmail("phuongtoaneducation@gmail.com");
        return "Da gui mail";
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
}
