package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;


//fa da mediator all'interno del pattern
public class GestorePubblicazioni implements IGestore {
    private Curatore curatore;
    private EmailSystem notifiche;
    private Messaggio messaggio;

    public GestorePubblicazioni(Curatore curatore, EmailSystem notifiche, Messaggio messaggio) {
        this.curatore = curatore;
        this.notifiche = notifiche;
        this.messaggio = messaggio;
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

    public void attendiRisposta(String token){
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
    }

    @Override
    public void inviaInformazioni(Componente sender, Messaggio event) {
        this.attendiRisposta(GestoreCreazioni.getTokenInformazioni());

        if (event instanceof FileInformazioniTestuale info) {
            info.getProdotto().aggiungiInformazioni(info);
        } else if (event instanceof FileInformazioniImmagini info){
            info.getProdotto().aggiungiInformazioni(info);
        }
    }

    @Override
    public void inviaProdotto(Componente sender, Messaggio event) {
        this.attendiRisposta(GestoreCreazioni.getTokenProdotto());
        if (event instanceof FileInformazioniProdotto info) {
            Prodotto prodotto = new Prodotto(info.getNome(), info.getAzienda());
            Marketplace.aggiungiProdotto(prodotto);
        }
    }
    @Override
    public void inviaPacchetto(Componente sender, Messaggio event) {
        this.attendiRisposta(GestoreCreazioni.getTokenPacchetto());
        if(event instanceof FileInformazioniPacchetto info){
            Pacchetto pacchetto = new Pacchetto(info.getNome(), info.getPrezzo(),info.getProdotti());
            Marketplace.aggiungiPacchetto(pacchetto);
        }

    }
}
