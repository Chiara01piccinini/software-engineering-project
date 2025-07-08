package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;

public class GestoreCreazioni implements IGestore {
    private DistributoreDiTipicita distributore;
    private Curatore curatore;
    private Trasformatore trasformatore;
    private GestorePubblicazioni gestorePubblicazioni;
    private static String tokenProdotto;

    public static String getTokenProdotto(){
        return tokenProdotto;
    }
    public GestoreCreazioni(){}

    @Override
    public void inviaInformazioni(Componente sender, Messaggio event) {

    }

    @Override
    public void inviaProdotto(Componente mittente, Messaggio event) {
        if (event instanceof FileInformazioniProdotto info && (mittente instanceof Produttore || mittente instanceof Trasformatore trasformatore1) ) {
            tokenProdotto = EmailSystem.inviaMail(curatore.getEmail(), "Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());
            if (curatore.approvaProdotto(info, (Venditore) mittente)){
                gestorePubblicazioni.inviaProdotto(mittente,event);
                mittente.riceviMessaggio("Informazioni approvate per il prodotto: " + info.getNome());
                EmailSystem.inviaMail(mittente.getEmail(), "Richiesta approvazione", "Il prodotto è stato pubblicato :" + info.getNome());
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