package org.example.app.model;

import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.IGestore;

public class Produttore extends Venditore {
    final Azienda azienda;

    public Produttore(String nome, String cognome, int matricola, String email, Azienda azienda, IGestore gestore) {
        super(nome, cognome, matricola, email, gestore);
        this.azienda = azienda;
    }

    public void inviaProdotto(IGestore gestore, FileInformazioniProdotto info){
        gestore.sendProduct(this, info);
    }
}

