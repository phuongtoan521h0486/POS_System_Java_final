package com.thd.pos_system_java_final.commands;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;

public class EmailCommand implements ICommand{// Apply command pattern
    private final JavaMailSenderImpl mailSender;
    private final MimeMessage message;

    public EmailCommand(JavaMailSenderImpl mailSender, MimeMessage message) {
        this.mailSender = mailSender;
        this.message = message;
    }

    @Override
    public void execute() {
        mailSender.send(message);
    }
}
