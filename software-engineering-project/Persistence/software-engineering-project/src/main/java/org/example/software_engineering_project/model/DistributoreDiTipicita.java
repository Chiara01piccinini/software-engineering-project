package org.example.software_engineering_project.model;

import org.example.software_engineering_project.controls.GestorePubblicazioni;
import org.example.software_engineering_project.controls.Session;

public class DistributoreDiTipicita extends Venditore{

    public DistributoreDiTipicita(Account account, int matricola, String email, Azienda azienda, Marketplace sistema, Session session) {
        super(account, matricola, email, azienda, sistema, session);
    }

    public void inviaPacchetto(GestorePubblicazioni gestore, FileInformazioniPacchetto pacchetto){
        gestore.inviaPacchetto(this,pacchetto);
    }
}

