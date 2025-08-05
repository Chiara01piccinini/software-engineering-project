package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;

public class GestoreCreazioni implements IGestore {
    private Curatore curatore;
    private GestorePubblicazioni gestorePubblicazioni;

    public GestoreCreazioni(GestorePubblicazioni gestorePubblicazioni, Curatore curatore) {
        this.gestorePubblicazioni = gestorePubblicazioni;
        this.curatore = curatore;
    }

    public Curatore getCuratore() {
        return curatore;
    }

    public void setCuratore(Curatore curatore) {
        this.curatore = curatore;
    }

    public GestorePubblicazioni getGestorePubblicazioni() {
        return gestorePubblicazioni;
    }

    public void setGestorePubblicazioni(GestorePubblicazioni gestorePubblicazioni) {
        this.gestorePubblicazioni = gestorePubblicazioni;
    }

    @Override
    public void inviaInformazioni(Componente sender, Messaggio event) {
        // Reindirizza al gestore delle pubblicazioni per approvazione
        gestorePubblicazioni.inviaInformazioni(sender, event);
    }

    @Override
    public void inviaProdotto(Componente sender, Messaggio event) {
        // Reindirizza al gestore delle pubblicazioni per approvazione
        gestorePubblicazioni.inviaProdotto(sender, event);
    }

    @Override
    public void inviaPacchetto(Componente sender, Messaggio event) {
        // Reindirizza al gestore delle pubblicazioni per approvazione
        gestorePubblicazioni.inviaPacchetto(sender, event);
    }

    // Metodo usato da GestorePubblicazioni dopo approvazione
    public void creaProdotto(FileInformazioniProdotto info, Componente sender) {
        Prodotto prodotto = new Prodotto(info.getNome(), info.getAzienda());
        Marketplace.aggiungiProdotto(prodotto);
        sender.riceviMessaggio("Prodotto creato: " + info.getNome());
    }

    public void creaPacchetto(FileInformazioniPacchetto info, Componente sender) {
        Pacchetto pacchetto = new Pacchetto(info.getNome(), info.getPrezzo(), info.getProdotti());
        Marketplace.aggiungiPacchetto(pacchetto);
        sender.riceviMessaggio("Pacchetto creato: " + info.getNome());
    }

    public void creaInformazioni(Messaggio info, Componente sender) {
        if (info instanceof FileInformazioniImmagini immagini) {
            immagini.getProdotto().aggiungiInformazioni(immagini);
            sender.riceviMessaggio("Immagini aggiunte per il prodotto: " + immagini.getNome());
        } else if (info instanceof FileInformazioniTestuale testo) {
            testo.getProdotto().aggiungiInformazioni(testo);
            sender.riceviMessaggio("Testo aggiunto per il prodotto: " + testo.getNome());
        }
    }
}