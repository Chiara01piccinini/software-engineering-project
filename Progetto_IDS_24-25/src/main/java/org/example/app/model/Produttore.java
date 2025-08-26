package org.example.app.model;

import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.IGestore;

public class Produttore extends Venditore {

    public Produttore(Account account, int matricola, String email, Azienda azienda) {
        super(account, matricola, email, azienda);
    }
    public void inviaProdotto(GestorePubblicazioni gestore, FileInformazioniProdotto info){
        gestore.inviaProdotto(this, info);
    }
}

