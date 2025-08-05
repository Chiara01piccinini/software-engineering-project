package org.example.app.view;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Date;
import java.util.UUID;

public class EmailSystem {
    private static final String mittenteSistema = "chrpccnn@gmail.com";
    private static final String passwordSistema = "uxyp sxlb lvvr tqgp";

    public static String inviaMail(String destinatario, String oggetto, String testo) {
        String token = UUID.randomUUID().toString();
        String oggettoConToken = oggetto + " [TOKEN:" + token + "]";

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
            message.setSubject(oggettoConToken);
            message.setText(testo);
            Transport.send(message);
            System.out.println("[EmailSystem] Email inviata a " + destinatario + " con oggetto: " + oggettoConToken);
        } catch (MessagingException e) {
            System.err.println("Errore invio email: " + e.getMessage());
        }
        return token;
    }

    public static Boolean leggiRispostaApprova(String token, Date dataMinima) {
        Boolean approved = null;
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", mittenteSistema, passwordSistema);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            System.out.println("Totale messaggi in INBOX: " + messages.length);
            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];
                Date receivedDate = message.getReceivedDate();
                if (receivedDate == null || receivedDate.before(dataMinima)) continue;
                String subject = message.getSubject();
                if (subject != null && subject.contains(token)) {
                    String body = "";
                    if (message.isMimeType("text/plain")) {
                        body = message.getContent().toString();
                    } else if (message.isMimeType("multipart/*")) {
                        Multipart mp = (Multipart) message.getContent();
                        for (int j = 0; j < mp.getCount(); j++) {
                            BodyPart bp = mp.getBodyPart(j);
                            body += bp.getContent().toString();
                        }
                    }
                    String bodyLower = body.toLowerCase();
                    if (bodyLower.contains("rifiuta")) {
                        approved = false;
                    } else if (bodyLower.contains("approva")) {
                        approved = true;
                    }
                    break;
                }
            }
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return approved;
    }
}
