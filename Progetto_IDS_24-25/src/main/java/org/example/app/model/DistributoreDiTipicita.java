package org.example.app.model;

import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.IGestore;

public class DistributoreDiTipicita extends Venditore{

    public DistributoreDiTipicita( String nome, String cognome, int matricola, String email, IGestore gestore) {
        super( nome, cognome, matricola, email, gestore);
    }
}
