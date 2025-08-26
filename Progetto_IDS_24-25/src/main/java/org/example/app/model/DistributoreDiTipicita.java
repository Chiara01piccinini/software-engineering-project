package org.example.app.model;

import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.IGestore;

public class DistributoreDiTipicita extends Venditore{

    public DistributoreDiTipicita(Account account, int matricola, String email, Azienda azienda) {
        super(account, matricola, email, azienda);
    }

    public void inviaPacchetto(GestorePubblicazioni gestore, FileInformazioniPacchetto pacchetto){
        gestore.inviaPacchetto(this,pacchetto);
    }
}
