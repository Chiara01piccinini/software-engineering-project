package org.example.app.model;

import org.example.app.controls.IGestore;

public class Trasformatore extends Venditore {
    final Azienda azienda;

    public Trasformatore( String nome, String cognome, int matricola, String email, Azienda azienda, IGestore gestore) {
        super( nome, cognome, matricola, email, gestore);
        this.azienda = azienda;
    }

    public void inviaProdotto(IGestore gestore, FileInformazioniProdotto info){
        gestore.inviaProdotto(this, info);
    }
}
