package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;

public class GestoreCreazioni implements IGestore {
    private DistributoreDiTipicita distributore;
    private Curatore curatore;
    private Trasformatore trasformatore;

    public GestoreCreazioni(){}

    @Override
    public void inviaInformazioni(Componente sender, Messaggio event) {

    }

    @Override
    public void inviaProdotto(Componente mittente, Messaggio event) {
        if (event instanceof FileInformazioniProdotto info && (mittente instanceof Produttore || mittente instanceof Trasformatore trasformatore1) ) {
            EmailSystem.inviaMail(curatore.getEmail(), "Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());

            if (curatore.approvaProdotto(info, (Venditore) mittente)){
                Prodotto prodotto = new Prodotto(info.getId(), info.getNome(), info.getAzienda());
                //todo il prodotto va aggiunto nel marketplace
                mittente.riceviMessaggio("Informazioni approvate per il prodotto: " + prodotto.getNome());
                EmailSystem.inviaMail(mittente.getEmail(), "Richiesta approvazione", "Il prodotto è stato pubblicato :" + prodotto.getNome());
            }else
                EmailSystem.inviaMail(mittente.getEmail(), "Richiesta approvazione", "La richiesta di pubblicare: " + info.getContenuto() + " è stata rifiutata");;
        }

    }

    @Override
    public void inviaPacchetto(Componente mittente, Messaggio event) {
        if (event instanceof FileInformazioniPacchetto info && (mittente instanceof DistributoreDiTipicita )) {
            EmailSystem.inviaMail(curatore.getEmail(), "Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());

            if (curatore.approvaPacchetto(info, (DistributoreDiTipicita) mittente)){
                Pacchetto pacchetto = new Pacchetto(info.getNome(), info.getId(), info.getPrezzo(),info.getProdotti());
                //todo il pacchetto va aggiunto nel marketplace dal gestore pubblicazioni
                mittente.riceviMessaggio("Informazioni approvate per il pacchetto: " + pacchetto.getNome());
                EmailSystem.inviaMail(mittente.getEmail(), "Richiesta approvazione", "Il pacchetto è stato pubblicato :" + pacchetto.getNome());
            }else
                EmailSystem.inviaMail(mittente.getEmail(), "Richiesta approvazione", "La richiesta di pubblicare: " + info.getContenuto() + " è stata rifiutata");;
        }
    }
}