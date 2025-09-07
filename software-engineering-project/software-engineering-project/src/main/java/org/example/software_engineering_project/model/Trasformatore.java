package org.example.software_engineering_project.model;


import org.example.software_engineering_project.controls.GestorePubblicazioni;
import org.example.software_engineering_project.controls.Session;

public class Trasformatore extends Venditore {

    public Trasformatore(Account account, int matricola, String email, Azienda azienda, Marketplace sistema, Session session) {
        super(account, matricola, email, azienda, sistema, session);
    }

    public void inviaProdotto(GestorePubblicazioni gestore, FileInformazioniProdotto info){
        gestore.inviaProdotto(this, info);
    }
}
