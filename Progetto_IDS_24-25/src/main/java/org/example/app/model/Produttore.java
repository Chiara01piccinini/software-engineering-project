package org.example.app.model;

import org.example.app.controls.IGestore;

public class Produttore extends Venditore {
    final Azienda azienda;

    public Produttore(String nome, String cognome, int matricola, String email, Azienda azienda) {
        super(nome, cognome, matricola, email);
        this.azienda = azienda;
    }

    public void inviaProdotto(IGestore gestore, FileInformazioniProdotto info){
        gestore.inviaProdotto(this, info);
    }
}

