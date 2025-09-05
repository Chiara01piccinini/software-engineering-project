package org.example.software_engineering_project.model;

import org.example.software_engineering_project.controls.*;
import org.example.software_engineering_project.view.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;


@RequestScope
public class Curatore extends Componente {

    private final Account account;
    private final EmailSystem notifiche;

    // Costruttore con iniezione di Session e Marketplace
    public Curatore(Account account, int matricola, String email, EmailSystem notifiche,
                    Marketplace sistema, Session session) {
        super(matricola, email, sistema, session);
        this.account = account;
        this.notifiche = notifiche;
        super.setAccount(account); // associa l'account anche nella superclasse
    }

    public boolean approvaProdotto(FileInformazioniProdotto prodotto, Venditore sender) {
        if (!super.session.isAuthenticated()) {
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
        if (!super.session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if (sender == null || sender.getEmail() == null) return false;
        if (pacchetto == null || pacchetto.getNome() == null || pacchetto.getContenuto() == null) return false;
        return true;
    }

    public boolean approvaEvento(FileInformazioniEvento evento, Animatore sender) {
        if (!super.session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if (sender == null || sender.getEmail() == null) return false;
        if (evento == null || evento.getNome() == null || evento.getContenuto() == null) return false;
        return true;
    }

    public boolean approvaAccount(FileInformazioniAccount info, Componente sender) {
        if (!super.session.isAuthenticated()) {
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
