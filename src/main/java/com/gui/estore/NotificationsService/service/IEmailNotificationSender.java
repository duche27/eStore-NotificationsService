package com.gui.estore.NotificationsService.service;


import javax.mail.MessagingException;

public interface IEmailNotificationSender {

    void sendSimpleMessage(String to, String subject, String text);

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
