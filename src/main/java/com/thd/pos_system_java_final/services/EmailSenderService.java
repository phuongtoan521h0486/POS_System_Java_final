package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.DataMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService implements MailService{
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gtvprimeofficial@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void sendHtmlMail(DataMail dataMail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        Context context = new Context();
        context.setVariables(dataMail.getProps());

        String html = templateEngine.process(templateName, context);

        helper.setTo(dataMail.getTo());
        helper.setSubject(dataMail.getSubject());
        helper.setText(html, true);

        mailSender.send(message);
    }
}
