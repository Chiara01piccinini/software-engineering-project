package org.example.app.model;

import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.IGestore;
//rappresenta tutti i venditori,ovvero coloro che possono vendere dei prrodotti nel marketplace
public abstract class Venditore  extends Componente{
    public Venditore( String nome, String cognome, int matricola, String email) {
        super(nome,cognome,matricola,email);
    }

    //invoca il gestore per inviare i file
    public void inviaFile(GestorePubblicazioni gestore,Messaggio info ){
        gestore.inviaInformazioni(this,info);
    };
    //stampa a video il messaggio ricevuto
    public void riceviMessaggio(String messaggio) {
        System.out.println("[Messaggio]: " + messaggio);
    }
}
