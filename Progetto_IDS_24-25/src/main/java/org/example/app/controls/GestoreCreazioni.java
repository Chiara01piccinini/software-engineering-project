package org.example.app.controls;

import org.example.app.model.*;
import org.example.app.view.MappaService;

public class GestoreCreazioni implements IGestore {
    private Curatore curatore;
    private GestorePubblicazioni gestorePubblicazioni;
    private MappaService mappa;
    private Marketplace sistema;


    public GestoreCreazioni(GestorePubblicazioni gestorePubblicazioni, Curatore curatore) {
        this.gestorePubblicazioni = gestorePubblicazioni;
        this.curatore = curatore;
        this.mappa = new MappaService();
    }

    public MappaService getMappa() {
        return mappa;
    }

    public void setMappa(MappaService mappa) {
        this.mappa = mappa;
    }

    public Marketplace getSistema() {
        return sistema;
    }

    public void setSistema(Marketplace sistema) {
        this.sistema = sistema;
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
    public void inviaEvento(Animatore sender,Messaggio event){
        gestorePubblicazioni.inviaEvento(sender,event);
    }

    // Metodo usato da GestorePubblicazioni dopo approvazione
    public void creaProdotto(FileInformazioniProdotto info, Componente sender) {
        if (sender instanceof Produttore produttore){
            ProdottoBase prodottoBase = new ProdottoBase(info.getNome(), info.getAzienda(), produttore);
            sistema.aggiungiProdotto(prodottoBase);
        } else if (sender instanceof  Trasformatore trasformatore){
            ProdottoElaborato prodottoElaborato = new ProdottoElaborato(info.getNome(), info.getAzienda(), trasformatore);
            sistema.aggiungiProdotto(prodottoElaborato);
        }
        sender.riceviMessaggio("Prodotto creato: " + info.getNome());
    }

    public void creaPacchetto(FileInformazioniPacchetto info, Componente sender) {
        if (info.getProdotti().size() > 1){
            for (int i = 0; i < info.getQuantita(); i++){
                Pacchetto pacchetto = new Pacchetto(info.getNome(), info.getPercentualeSconto(), info.getProdotti(), info.getQuantita());
                sistema.aggiungiPacchetto(pacchetto);
            }
            sender.riceviMessaggio("Pacchetto creato: " + info.getNome());
        }
    }

    public void creaInformazioni(Messaggio info, Componente sender) {
        if (info instanceof FileInformazioniImmagini immagini) {
            immagini.getElemento().aggiungiInformazioni(immagini);
            sender.riceviMessaggio("Immagini aggiunte per il prodotto: " + immagini.getNome());
        } else if (info instanceof FileInformazioniTestuale testo) {
            testo.getElemento().aggiungiInformazioni(testo);
            sender.riceviMessaggio("Testo aggiunto per il prodotto: " + testo.getNome());
        }
    }

    public void creaEvento(FileInformazioniEvento info, Animatore sender) {
        Evento evento =new Evento(info.getData(),info.getOrario(),info.getLuogo(),info.getNome(),info.getBiglietti(),info.getDescrizione());
        sistema.aggiungiEvento(evento);
        mappa.getMappa().put(info.getLuogo(),info.getNome());
        sender.riceviMessaggio("evento creato :" + evento.getNome());

    }

    public void creaAccount(FileInformazioniAccount info, Componente sender) {
        tipoAccount tipo;
        if (sender instanceof Produttore) {
            tipo = tipoAccount.PRODUTTORE;
            mappa.getMappa().put(
                    ((Produttore) sender).getAzienda().getPosition(),
                    ((Produttore) sender).getAzienda().getName()
            );
        } else if (sender instanceof Trasformatore) {
            tipo = tipoAccount.TRASFORMATORE;
            mappa.getMappa().put(
                    ((Trasformatore) sender).getAzienda().getPosition(),
                    ((Trasformatore) sender).getAzienda().getName()
            );
        } else if (sender instanceof DistributoreDiTipicita) {
            tipo = tipoAccount.DISTRIBUTOREDITIPICITA;
        } else if (sender instanceof Animatore) {
            tipo = tipoAccount.ANIMATORE;
        } else {
            tipo = tipoAccount.GENERICO;
        }
        Account account = new Account(info.getNomeUtente(), info.getPassword(), tipo);
        sender.riceviMessaggio("account creato: " + info.getContenuto());
        sistema.aggiungiAccount(account);
    }
    public void modificaDisponibilità(Prodotto prodotto,int nq){

        prodotto.setQuantita(nq);
    }
}
