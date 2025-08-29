package org.example.app.model;

import org.example.app.controls.Session;
import org.example.app.view.EmailSystem;

public class Curatore extends Componente {
    private Account account;
    private EmailSystem notifiche;

    public Curatore(Account account, int matricola, String email, EmailSystem notifiche) {
        super(matricola, email);
        this.account = account;
        this.notifiche = notifiche;
    }

    public boolean approvaProdotto(FileInformazioniProdotto prodotto, Venditore sender) {
        if (!Session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if (sender == null || sender.getEmail() == null || !sender.getEmail().contains("@")) {
            System.out.println("[Curatore] Mittente non valido");
            return false;
        }
        if (prodotto == null || prodotto.getNome() == null || prodotto.getContenuto() == null) {
            System.out.println("[Curatore] Dati prodotto non validi");
            return false;
        }
        return true;
    }

    public boolean approvaPacchetto(FileInformazioniPacchetto pacchetto, DistributoreDiTipicita sender) {
        if (!Session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if (sender == null || sender.getEmail() == null) return false;
        if (pacchetto == null || pacchetto.getNome() == null || pacchetto.getContenuto() == null) return false;
        return true;
    }

    public boolean approvaEvento(FileInformazioniEvento evento, Animatore sender) {
        if (!Session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if (sender == null || sender.getEmail() == null) return false;
        if (evento == null || evento.getNome() == null || evento.getContenuto() == null) return false;
        return true;
    }

    public boolean approvaAccount(FileInformazioniAccount info, Componente sender) {
        if (!Session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if (sender == null || sender.getEmail() == null) return false;
        if (info == null || info.getContenuto() == null) return false;
        return true;
    }

    @Override
    public void riceviMessaggio(String messaggio) {
        System.out.println("[Curatore]: " + messaggio);
    }
}
