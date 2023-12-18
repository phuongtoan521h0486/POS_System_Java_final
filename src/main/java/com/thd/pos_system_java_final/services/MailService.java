package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.DataMail;

import javax.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMail dataMail, String templateName) throws MessagingException;
}
