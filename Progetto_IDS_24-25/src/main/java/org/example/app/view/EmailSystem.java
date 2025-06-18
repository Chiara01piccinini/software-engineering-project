package org.example.app.view;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailSystem {

    private static final String mittenteSistema = "chrpccnn@gmail.com";
    private static final String passwordSistema = "oini mrby cwmp ddhi";

    public static void inviaMail(String destinatario, String oggetto, String testo) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mittenteSistema, passwordSistema);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mittenteSistema));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(oggetto);
            message.setText(testo);

            Transport.send(message);
            System.out.println("Email inviata a " + destinatario);
        } catch (MessagingException e) {
            System.err.println("Errore nell'invio email: " + e.getMessage());
        }
    }
}