package org.example.app.model;

import org.example.app.controls.IGestore;

public class Trasformatore extends Venditore {
    final Azienda azienda;

    public Trasformatore( String nome, String cognome, int matricola, String email, Azienda azienda) {
        super( nome, cognome, matricola, email);
        this.azienda = azienda;
    }

    public void inviaProdotto(IGestore gestore, FileInformazioniProdotto info){
        gestore.inviaProdotto(this, info);
    }
}
