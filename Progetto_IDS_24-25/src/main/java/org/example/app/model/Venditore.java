package org.example.app.model;

import org.example.app.controls.GestoreCreazioni;
import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.GestoreSponsorizzazioni;
import org.example.app.controls.Session;

import java.math.BigDecimal;

//rappresenta tutti i venditori,ovvero coloro che possono vendere dei prrodotti nel marketplace
public abstract class Venditore  extends Componente{
    private Azienda azienda;
    public Venditore(Account account, int matricola, String email,Azienda azienda) {
        super(account, matricola, email);
        this.azienda=azienda;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    //invoca il gestore per inviare i file
    public void inviaFile(GestorePubblicazioni gestore,Messaggio info ){
        gestore.inviaInformazioni(this,info);
    };


    public boolean attivaLaVendita(Prodotto p, BigDecimal prezzo, int quantita){
        if (p != null && prezzo.compareTo(BigDecimal.ZERO) > 0 && quantita > 0) {
            p.setPrezzo(prezzo);
            p.setVendita(true);
            p.setQuantità(quantita);
            return true;
        }
        return false;
    }
    public void richiediSponsorizzazione(GestoreSponsorizzazioni gestore,
                                         Messaggio messaggio,
                                         Piattaforme tipo) {
        if (!Session.isAuthenticated()) {
        throw new SecurityException("Operazione non consentita: utente non autenticato");
    }
        // Esempio di check ruolo (solo ACQUIRENTE può prenotare)
        if (Session.getCurrentUser().getTipologia() == tipoAccount.GENERICO || Session.getCurrentUser().getTipologia() == tipoAccount.ANIMATORE) {
            throw new SecurityException("Operazione non consentita: ruolo non autorizzato");
        }
        gestore.pubbliacaContenuto(this, messaggio, tipo);
    }
    public void richiediModificaDisponibilità(GestoreCreazioni gestore,Prodotto prodotto, int nq){
        if (!Session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        // Esempio di check ruolo (solo ACQUIRENTE può prenotare)
        if (Session.getCurrentUser().getTipologia() == tipoAccount.GENERICO || Session.getCurrentUser().getTipologia() == tipoAccount.ANIMATORE) {
            throw new SecurityException("Operazione non consentita: ruolo non autorizzato");
        }
        if(this.azienda==prodotto.getAzienda()){
        gestore.modificaDisponibilità(prodotto,nq);
       }
    }
    public void modificaPrezzo(Prodotto prodotto, BigDecimal np){
        if (!Session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        // Esempio di check ruolo (solo ACQUIRENTE può prenotare)
        if (Session.getCurrentUser().getTipologia() == tipoAccount.GENERICO || Session.getCurrentUser().getTipologia() == tipoAccount.ANIMATORE) {
            throw new SecurityException("Operazione non consentita: ruolo non autorizzato");
        }
        if (prodotto.getAzienda()==this.azienda && prodotto.getVendita() ){
            prodotto.setPrezzo(np);}
    }
}