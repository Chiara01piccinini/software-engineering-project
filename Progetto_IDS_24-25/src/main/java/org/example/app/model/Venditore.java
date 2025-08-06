package org.example.app.model;

import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.IGestore;

import java.math.BigDecimal;

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

    public boolean attivaLaVendita(Prodotto p, BigDecimal prezzo, int quantita){
        if (p != null && prezzo.compareTo(BigDecimal.ZERO) > 0 && quantita > 0) {
            p.setPrezzo(prezzo);
            p.setVendita(true);
            p.setQuantita(quantita);
            return true;
        }
        return false;
    }
}
