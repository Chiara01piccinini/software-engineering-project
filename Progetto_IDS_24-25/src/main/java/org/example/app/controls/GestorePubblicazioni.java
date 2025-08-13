package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestorePubblicazioni implements IGestore {
    private Curatore curatore;
    private EmailSystem notifiche;
    private GestoreCreazioni gestoreCreazioni;

    public GestorePubblicazioni(Curatore curatore, EmailSystem notifiche) {
        this.curatore = curatore;
        this.notifiche = notifiche;
    }

    public Curatore getCuratore() {
        return curatore;
    }

    public void setCuratore(Curatore curatore) {
        this.curatore = curatore;
    }

    public EmailSystem getNotifiche() {
        return notifiche;
    }

    public void setNotifiche(EmailSystem notifiche) {
        this.notifiche = notifiche;
    }

    public GestoreCreazioni getGestoreCreazioni() {
        return gestoreCreazioni;
    }

    public void setGestoreCreazioni(GestoreCreazioni gestoreCreazioni) {
        this.gestoreCreazioni = gestoreCreazioni;
    }

    // Polling rimane qui
    private Boolean attendiRisposta(String token, Date dataInvio) {
        int maxTentativi = 10;
        int tentativo = 0;
        long delaySeconds = 30;
        final long maxDelaySeconds = 600;
        Boolean approvato = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try { Thread.sleep(30000); } catch (InterruptedException ignored) {}
        while (tentativo < maxTentativi) {
            System.out.println("[DEBUG] Tentativo " + (tentativo + 1) + " - Ora sistema: " + sdf.format(new Date()));
            approvato = EmailSystem.leggiRispostaApprova(token, dataInvio);
            if (approvato != null) break;
            try { Thread.sleep(delaySeconds * 1000); } catch (InterruptedException e) { break; }
            delaySeconds = Math.min(delaySeconds * 2, maxDelaySeconds);
            tentativo++;
        }
        return approvato;
    }

    @Override
    public void inviaInformazioni(Componente sender, Messaggio event) {
        if (event instanceof FileInformazioniTestuale info) {
            Date dataInvio = new Date();
            // usa il Curatore solo per inviare mail e ricevere token
            String token = curatore.richiediApprovazione("Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());
            Boolean approvato = attendiRisposta(token, dataInvio);
            if (Boolean.TRUE.equals(approvato)) {
                gestoreCreazioni.creaInformazioni(info, sender);
                EmailSystem.inviaMail(sender.getEmail(), "Informazioni approvate", "Le informazioni sono state aggiunte per il prodotto: " + info.getNome());
            } else {
                EmailSystem.inviaMail(sender.getEmail(), "Informazioni rifiutate", "La richiesta è stata rifiutata");
            }
        }
    }

    @Override
    public void inviaProdotto(Componente sender, Messaggio event) {
        if (event instanceof FileInformazioniProdotto info) {
            Date dataInvio = new Date();
            String token = curatore.richiediApprovazione("Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());
            Boolean approvato = attendiRisposta(token, dataInvio);
            // usa curatore solo come validatore senza polling
            if (Boolean.TRUE.equals(approvato) && curatore.approvaProdotto(info, (Venditore) sender)) {
                gestoreCreazioni.creaProdotto(info, sender);
                EmailSystem.inviaMail(sender.getEmail(), "Prodotto approvato", "Il prodotto è stato pubblicato: " + info.getNome());
            } else {
                EmailSystem.inviaMail(sender.getEmail(), "Prodotto rifiutato", "La richiesta è stata rifiutata");
            }
        }

    }

    @Override
    public void inviaPacchetto(Componente sender, Messaggio event) {
        if (event instanceof FileInformazioniPacchetto info) {
            Date dataInvio = new Date();
            String token = curatore.richiediApprovazione("Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());
            Boolean approvato = attendiRisposta(token, dataInvio);
            if (Boolean.TRUE.equals(approvato) && curatore.approvaPacchetto(info, (DistributoreDiTipicita) sender)) {
                gestoreCreazioni.creaPacchetto(info, sender);
                EmailSystem.inviaMail(sender.getEmail(), "Pacchetto approvato", "Il pacchetto è stato pubblicato: " + info.getNome());
            } else {
                EmailSystem.inviaMail(sender.getEmail(), "Pacchetto rifiutato", "La richiesta è stata rifiutata");
            }
        }
    }
    public void inviaEvento (Animatore sender,Messaggio event){
        if(event instanceof FileInformazioniEvento info && sender instanceof Animatore){
            Date dataInvio = new Date();
            String token = curatore.richiediApprovazione("Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());
            Boolean approvato=attendiRisposta(token,dataInvio);
            if (Boolean.TRUE.equals(approvato) && curatore.approvaEvento(info, sender)) {
                gestoreCreazioni.creaEvento(info, sender);
                EmailSystem.inviaMail(sender.getEmail(), "Pacchetto approvato", "Il pacchetto è stato pubblicato: " + info.getNome());
            } else {
                EmailSystem.inviaMail(sender.getEmail(), "Pacchetto rifiutato", "La richiesta è stata rifiutata");
            }

        }

    }
    public void inviaAccount(Componente sender,Messaggio event){
        if(event instanceof FileInformazioniAccount info){
            Date dataInvio = new Date();
            String token = curatore.richiediApprovazione("Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());
            Boolean approvato=attendiRisposta(token,dataInvio);
            if (Boolean.TRUE.equals(approvato) && curatore.approvaAccount(info, sender)) {
                gestoreCreazioni.creaAccount(info, sender);
                EmailSystem.inviaMail(sender.getEmail(), "Account approvato", "l'account è stato pubblicato: " + info.getContenuto());
            } else {
                EmailSystem.inviaMail(sender.getEmail(), "Account rifiutato", "La richiesta è stata rifiutata");
            }

        }
    }
}
