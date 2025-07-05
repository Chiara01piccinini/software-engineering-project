package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;


//fa da mediator all'interno del pattern
public class GestorePubblicazioni implements IGestore {
    private Curatore curatore;
    private EmailSystem notifiche;
    private Prodotto prodotto;

    public GestorePubblicazioni(Curatore curatore, EmailSystem notifiche, Prodotto prodotto) {
        this.curatore = curatore;
        this.notifiche = notifiche;
        this.prodotto = prodotto;
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

@Override
public void inviaInformazioni(Componente mittente, Messaggio messaggio) {
    if (messaggio instanceof IFileInformazioni info && mittente instanceof Venditore) {
//chiama EmailSystem per inviare il messaggio
        String token = EmailSystem.inviaMail(curatore.getEmail(),
                "Esito richiesta per  " + prodotto.getNome(),
                "Contenuto da approvare: " + info.getContenuto());

        System.out.println("[Curatore] In attesa di risposta email per approvazione...");
//attende una risposta dal curatore facendo pooling ogni 30 sec per 20 volte
        Boolean approvato = null;
        int maxTentativi = 20;
        int tentativo = 0;

        while (tentativo < maxTentativi && approvato == null) {
            try {
                Thread.sleep(30000); // 30 secondi
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            approvato = EmailSystem.leggiRispostaApprova(token);
            tentativo++;
        }
//gestione della risposta
        if (Boolean.TRUE.equals(approvato)) {
            prodotto.aggiungiInformazioni(info);
            mittente.riceviMessaggio("Informazioni approvate per il prodotto: " + prodotto.getNome());
        } else if (Boolean.FALSE.equals(approvato)) {
            EmailSystem.inviaMail(mittente.getEmail(),
                    "Approvazione negata " + prodotto.getNome(),
                    "Approvazione negata per informazioni sul prodotto: " + prodotto.getNome());
        } else {
            System.out.println(" Nessuna risposta ricevuta entro il tempo limite.");
        }
    }
}
    @Override
    public void inviaProdotto(Componente sender, Messaggio event) {

    }
    @Override
    public void inviaPacchetto(Componente sender, Messaggio event) {

    }
}
