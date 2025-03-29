package com.analiseativo;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    private final String emailTo;
    private final String smtpHost;
    private final String smtpPort;
    private final String smtpUsername;
    private final String smtpPassword;

    public EmailService() {
        ConfigLoader config = new ConfigLoader();
        this.emailTo = config.getProperty("email.to");
        this.smtpHost = config.getProperty("smtp.host");
        this.smtpPort = config.getProperty("smtp.port");
        this.smtpUsername = config.getProperty("smtp.username");
        this.smtpPassword = config.getProperty("smtp.password");
    }

    public void enviarAlerta(String assunto, String mensagem) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtpUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(assunto);
            message.setText(mensagem);

            Transport.send(message);
            System.out.println("Alerta enviado: " + assunto);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}