package org.example.app.view;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;//libreria per generare token univoci a 128b
//gestisce invio e ricezione delle email
public class EmailSystem {
//credenziali di sistema
    private static final String mittenteSistema = "chrpccnn@gmail.com";
    private static final String passwordSistema = "oini mrby cwmp ddhi";

    /**
     * Invia una mail con token univoco nel subject per il tracciamento.
     */
    public static String inviaMail(String destinatario, String oggetto, String testo) {
        String token = UUID.randomUUID().toString(); // creazione token univoco
        String oggettoConToken = oggetto + " [TOKEN:" + token + "]";
//proprieta SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");//protocollo di criptaggio
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

        return token; // restituisce il token per il polling
    }

    public static Boolean leggiRispostaApprova(String token) {
        Boolean approved = null; // null = nessuna risposta, true = approvato, false = rifiutato
// proprietà IMAP
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
//connessione a INBOX
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", mittenteSistema, passwordSistema);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.getMessages();

            // Legge le email dalla più recente alla più vecchia
            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];

                Address[] froms = message.getFrom();
                String sender = froms == null ? "Unknown" : froms[0].toString();
                String subject = message.getSubject();
                String date = message.getReceivedDate() != null ? message.getReceivedDate().toString() : "Unknown date";

                System.out.println("==> Email ricevuta");
                System.out.println(" - Mittente: " + sender);
                System.out.println(" - Subject: " + subject);
                System.out.println(" - Data/Ora ricezione: " + date);
//verifica che la mailcontenga il token e non sia stata ancora letta
                if (subject != null && subject.contains(token) && !message.isSet(Flags.Flag.SEEN)) {
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
//analizza il corpo del messaggio
                    System.out.println(" - Contenuto: " + body);

                    if (body.toLowerCase().contains("rifiuta")) {
                        approved = false;
                        System.out.println(" RIFIUTO TROVATO");
                    } else if (body.toLowerCase().contains("approva")) {
                        approved = true;
                        System.out.println(" APPROVAZIONE TROVATA");
                    } else {
                        approved = false;
                        System.out.println(" Né approvazione né rifiuto trovati nel corpo email.");
                    }
//imposta il messaggio come letto
                    message.setFlag(Flags.Flag.SEEN, true);
                    break;
                }
            }
//chiusura connessione co inbox
            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return approved;
    }
}
