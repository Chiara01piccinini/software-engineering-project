package org.example.app.model;

import org.example.app.controls.IGestore;

public class Curatore extends Componente{
    public Curatore(String nome,String  cognome,int matricola, String email, IGestore gestore) {
        super(nome,cognome,matricola,email, gestore);
    }
//todo metodo per approvare i contenuti inviati
    public boolean approva(IFileInformazioni info, Venditore venditore, Prodotto prodotto) {
        return true;
    }
    //stampa a video ilmessaggio ricevuto
    @Override
    public void riceviMessaggio(String messaggio) {
        System.out.println("[Curatore]: " + messaggio);
    }
    public boolean approvaProdotto(FileInformazioniProdotto prodotto, Venditore venditore){return true;}
}
