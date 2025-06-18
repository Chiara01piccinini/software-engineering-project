package org.example.app.controls;

import jakarta.mail.BodyPart;
import jakarta.mail.Flags;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
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

   /*ublic Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }*/

    public EmailSystem getNotifiche() {
        return notifiche;
    }

    public void setNotifiche(EmailSystem notifiche) {
        this.notifiche = notifiche;
    }
// metodo per inviare le notifiche ai vari componenti usandola classe EmailSystem
    @Override
    public void send(Componente mittente, Messaggio messaggio) {
        if (messaggio instanceof IFileInformazioni info && mittente instanceof Venditore) {
            EmailSystem.inviaMail(curatore.getEmail(), "Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());

            if (curatore.approva(info, (Venditore) mittente, prodotto)) {
                prodotto.aggiungiInformazioni(info);
                mittente.riceviMessaggio("Informazioni approvate per il prodotto: " + prodotto.getNome());
            } else {
                EmailSystem.inviaMail(mittente.getEmail(), "Richiesta approvazione", "approvazione negata per informazini sul prodotto :" + prodotto.getNome());
            }
        }
    }
}
