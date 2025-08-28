package org.example.app.model;

import org.example.app.view.EmailSystem;
import java.util.Date;

public class Curatore extends Componente {
    private EmailSystem notifiche;

    public Curatore(Account account, int matricola, String email,EmailSystem notifiche) {
        super(account, matricola, email);
        this.notifiche = notifiche;
    }

    // Solo invia la mail e restituisce il token
    public String richiediApprovazione(String oggetto, String testo) {
        System.out.println("[Curatore] Invio richiesta approvazione...");
        return notifiche.inviaMail(getEmail(), oggetto, testo);
    }

    // Metodo di approvazione che ora è passivo e ritorna true/false basato su parametri esterni
    public Boolean approvaProdotto(FileInformazioniProdotto prodotto, Venditore venditore) {
        System.out.println("[Curatore] approvaProdotto chiamato, ma non esegue polling.");
        // Solo segnaposto, la vera approvazione è gestita dal Gestore
        return true;
    }

    public Boolean approvaPacchetto(FileInformazioniPacchetto pacchetto, DistributoreDiTipicita distributore) {
        System.out.println("[Curatore] approvaPacchetto chiamato, ma non esegue polling.");
        return true;
    }

    public Boolean approvaInformazioni(Messaggio info, Venditore venditore, Prodotto prodotto) {
        System.out.println("[Curatore] approvaInformazioni chiamato, ma non esegue polling.");
        return true;
    }

    @Override
    public void riceviMessaggio(String messaggio) {
        System.out.println("[Curatore]: " + messaggio);
    }

    public void confermaApprovazione(Messaggio info) {
        System.out.println("[Curatore] Approvazione ricevuta per: " + info.getNome());
    }

    public boolean approvaEvento(FileInformazioniEvento info, Animatore sender) {
        System.out.println("[Curatore] approvaEvento chiamato"+info.getNome());
        return true;
    }

    public boolean approvaAccount(FileInformazioniAccount info, Componente sender) {
        System.out.println("[Curatore] approvaAccount " + info.getContenuto());
        return true;
    }
}
