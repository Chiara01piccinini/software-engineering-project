package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;

public class GestoreCreazioni implements IGestore {
    private DistributoreDiTipicita distributore;
    private Curatore curatore;
    private Trasformatore trasformatore;
    private GestorePubblicazioni gestorePubblicazioni;
    private static String tokenProdotto;
    private static String tokenPacchetto;
    private static String tokenInformazioni;

    public static String getTokenProdotto(){
        return tokenProdotto;
    }

    public static String getTokenPacchetto(){
        return tokenPacchetto;
    }

    public static String getTokenInformazioni(){return tokenInformazioni;}

    public GestoreCreazioni(GestorePubblicazioni gestorePubblicazioni, Curatore curatore){
        this.gestorePubblicazioni = gestorePubblicazioni;
        this.curatore = curatore;
    }

    @Override
    public void inviaInformazioni(Componente sender, Messaggio event) {
        if (event instanceof FileInformazioniTestuale info && sender instanceof Venditore) {
            //chiama EmailSystem per inviare il messaggio
            tokenInformazioni = EmailSystem.inviaMail(curatore.getEmail(),"Esito richiesta per  " + info.getNome(),"Contenuto da approvare: " + info.getContenuto());

            if (curatore.approvaInformazioni(info, (Venditore) sender, info.getProdotto())){
                gestorePubblicazioni.inviaInformazioni(sender,event);
                sender.riceviMessaggio("Informazioni approvate per il prodotto: " + info.getNome());
                EmailSystem.inviaMail(sender.getEmail(), "Richiesta approvazione", "Le informazioni sono state aggiunte al prodotto :" + info.getNome());
            }else
                EmailSystem.inviaMail(sender.getEmail(), "Richiesta approvazione", "La richiesta di pubblicare: " + info.getContenuto() + " è stata rifiutata");
        }
        if (event instanceof FileInformazioniImmagini info && sender instanceof Venditore) {
            //chiama EmailSystem per inviare il messaggio
            tokenInformazioni = EmailSystem.inviaMail(curatore.getEmail(),"Esito richiesta per  " + info.getNome(),"Contenuto da approvare: " + info.getContenuto());
            if (curatore.approvaInformazioni(info, (Venditore) sender, info.getProdotto())){
                gestorePubblicazioni.inviaInformazioni(sender,event);
                sender.riceviMessaggio("Immagini approvate per il prodotto: " + info.getNome());
                EmailSystem.inviaMail(sender.getEmail(), "Richiesta approvazione", "Le immagini sono state aggiunte al prodotto :" + info.getNome());
            }else
                EmailSystem.inviaMail(sender.getEmail(), "Richiesta approvazione", "La richiesta di pubblicare: " + info.getContenuto() + " è stata rifiutata");
        }
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
            tokenPacchetto= EmailSystem.inviaMail(curatore.getEmail(), "Richiesta approvazione", "Contenuto da approvare: " + info.getContenuto());
            if (curatore.approvaPacchetto(info, (DistributoreDiTipicita) mittente)){
                gestorePubblicazioni.inviaPacchetto(mittente,event);
                mittente.riceviMessaggio("Informazioni approvate per il pacchetto: " + info.getNome());
                EmailSystem.inviaMail(mittente.getEmail(), "Richiesta approvazione", "Il pacchetto è stato pubblicato :" + info.getNome());
            }else
                EmailSystem.inviaMail(mittente.getEmail(), "Richiesta approvazione", "La richiesta di pubblicare: " + info.getContenuto() + " è stata rifiutata");;
        }
    }
}