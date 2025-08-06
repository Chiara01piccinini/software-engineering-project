package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.EmailSystem;

public class GestoreCreazioni implements IGestore {
    private Curatore curatore;
    private GestorePubblicazioni gestorePubblicazioni;

    public GestoreCreazioni(GestorePubblicazioni gestorePubblicazioni, Curatore curatore) {
        this.gestorePubblicazioni = gestorePubblicazioni;
        this.curatore = curatore;
    }

    public Curatore getCuratore() {
        return curatore;
    }

    public void setCuratore(Curatore curatore) {
        this.curatore = curatore;
    }

    public GestorePubblicazioni getGestorePubblicazioni() {
        return gestorePubblicazioni;
    }

    public void setGestorePubblicazioni(GestorePubblicazioni gestorePubblicazioni) {
        this.gestorePubblicazioni = gestorePubblicazioni;
    }

    @Override
    public void inviaInformazioni(Componente sender, Messaggio event) {
        // Reindirizza al gestore delle pubblicazioni per approvazione
        gestorePubblicazioni.inviaInformazioni(sender, event);
    }

    @Override
    public void inviaProdotto(Componente sender, Messaggio event) {
        // Reindirizza al gestore delle pubblicazioni per approvazione
        gestorePubblicazioni.inviaProdotto(sender, event);
    }

    @Override
    public void inviaPacchetto(Componente sender, Messaggio event) {
        // Reindirizza al gestore delle pubblicazioni per approvazione
        gestorePubblicazioni.inviaPacchetto(sender, event);
    }

    // Metodo usato da GestorePubblicazioni dopo approvazione
    public void creaProdotto(FileInformazioniProdotto info, Componente sender) {
        if (sender instanceof Produttore produttore){
            ProdottoBase prodottoBase = new ProdottoBase(info.getNome(), info.getAzienda(), produttore);
            Marketplace.aggiungiProdotto(prodottoBase);
        } else if (sender instanceof  Trasformatore trasformatore){
            ProdottoElaborato prodottoElaborato = new ProdottoElaborato(info.getNome(), info.getAzienda(), trasformatore);
            Marketplace.aggiungiProdotto(prodottoElaborato);
        }
        sender.riceviMessaggio("Prodotto creato: " + info.getNome());
    }

    public void creaPacchetto(FileInformazioniPacchetto info, Componente sender) {
        if (info.getProdotti().size() > 1){
            for (int i = 0; i < info.getQuantita(); i++){
                Pacchetto pacchetto = new Pacchetto(info.getNome(), info.getPercentualeSconto(), info.getProdotti());
                Marketplace.aggiungiPacchetto(pacchetto);
            }
            sender.riceviMessaggio("Pacchetto creato: " + info.getNome());
        }
    }

    public void creaInformazioni(Messaggio info, Componente sender) {
        if (info instanceof FileInformazioniImmagini immagini) {
            immagini.getProdotto().aggiungiInformazioni(immagini);
            sender.riceviMessaggio("Immagini aggiunte per il prodotto: " + immagini.getNome());
        } else if (info instanceof FileInformazioniTestuale testo) {
            testo.getProdotto().aggiungiInformazioni(testo);
            sender.riceviMessaggio("Testo aggiunto per il prodotto: " + testo.getNome());
        }
    }
}