package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.DTO.DataMail;

import com.thd.pos_system_java_final.commands.EmailCommand;
import com.thd.pos_system_java_final.commands.ICommand;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService implements MailService{

    // Apply Singleton pattern
    private EmailSenderService() {
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("gtvprimeofficial@gmail.com");
        mailSender.setPassword("jihfblsarmerterk");

        java.util.Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
    }

    private static EmailSenderService instance = new EmailSenderService();

    public static EmailSenderService getInstance() {
        return instance;
    }
    // Apply Singleton pattern

    //reciever
    private final JavaMailSenderImpl mailSender;

    @Override
    public void sendHtmlMail(DataMail dataMail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        Context context = new Context();
        context.setVariables(dataMail.getProps());

        SpringTemplateEngine templateEngine = getSpringTemplateEngine();
        String html = templateEngine.process(templateName, context);

        helper.setTo(dataMail.getTo());
        helper.setSubject(dataMail.getSubject());
        helper.setText(html, true);

        // Apply command pattern
        ICommand emailCommand = new EmailCommand(mailSender, message);
        emailCommand.execute();
    }

    public SpringTemplateEngine getSpringTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }
}
