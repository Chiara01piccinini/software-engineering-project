package org.example.software_engineering_project.model;

import org.example.software_engineering_project.controls.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;

@Component
@RequestScope
public class Componente {

    private Account account;
    private final int matricola;
    private final String email;
    private String nome;
    private String cognome;
    private final ArrayList<String> prenotazioni;
    private final Marketplace sistema;
    protected final Session session;

    // Costruttore con iniezione di Session
    public Componente(int matricola, String email, Marketplace sistema, Session session) {
        this.matricola = matricola;
        this.email = email;
        this.prenotazioni = new ArrayList<>();
        this.sistema = sistema;
        this.session = session;
    }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public String getEmail() { return email; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public int getMatricola() { return matricola; }

    public void getPrenotazioni() {
        for (String p : this.prenotazioni) {
            System.out.println(p);
        }
    }

    public void prenotaEvento(Evento evento, int np) {
        if (!session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }

        if (evento.getBiglietti() >= np) {
            evento.setBiglietti(evento.getBiglietti() - np);
            System.out.println("Prenotazione di " + np + " biglietti per l'evento " + evento.getNome() + " avvenuta");
            prenotazioni.add(
                    "prenotazione per " + evento.getNome() +
                            " numero biglietti: " + np +
                            " data: " + evento.getData() +
                            " orario: " + evento.getOrario() +
                            " luogo: " + evento.getLuogo().getNome()
            );
        } else {
            System.out.println("Biglietti insufficienti per l'evento " + evento.getNome());
        }
    }

    public void visualizzaContenuti() {
        System.out.println(this.sistema.getProdotti());
        System.out.println(this.sistema.getEventi());
        System.out.println(this.sistema.getPacchetti());
    }

    public void visualizzaProdotto(Prodotto prodotto) {
        if (sistema.getProdotti().containsValue(prodotto)) {
            System.out.println("visualizzazione prodotto:" +
                    prodotto.getNome() +
                    " azienda produttrice:" + prodotto.getAzienda() +
                    " quantità disponibile: " + prodotto.getQuantita() +
                    " prezzo: " + prodotto.calcolaPrezzo() +
                    " descrizione:" + prodotto.getDescrizione());
        }
    }

    public void visualizzaPacchetto(Pacchetto pacchetto) {
        if (sistema.getPacchetti().containsValue(pacchetto)) {
            System.out.println("visualizzazione pacchetto:" + pacchetto.getNome() +
                    " quantità disponibile: " + pacchetto.getQuantita() +
                    " descrizione: " + pacchetto.getDescrizione() +
                    " prodotti compresi: " + pacchetto.getProdotti() +
                    " prezzo: " + pacchetto.calcolaPrezzo());
        }
    }

    public void visualizzaEvento(Evento evento) {
        if (sistema.getEventi().containsValue(evento)) {
            System.out.println("visualizzazione evento:" + evento.getNome() +
                    " posti disponibili: " + evento.getBiglietti() +
                    " descrizione: " + evento.getDescrizione() +
                    " data: " + evento.getData() +
                    " orario: " + evento.getOrario() +
                    " luogo: " + evento.getLuogo());
        }
    }

    public void acquista(GestoreAcquisti gestore, IElemento elemento, int quantità) {
        if (!session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if (elemento instanceof Prodotto) {
            gestore.acquistaProdotto(this, (Prodotto) elemento, quantità);
        } else if (elemento instanceof Pacchetto) {
            gestore.acquistaPacchetto(this, (Pacchetto) elemento, quantità);
        }
    }

    public void aggiungiAlCarrello(IElemento elemento, int quantità) {
        if (!session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        this.account.aggiungiElemento(elemento, quantità);
        System.out.println("[Carrello] Aggiunto " + quantità + "x " + elemento.getNome());
    }

    public void acquistaCarrello(GestoreAcquisti gestore) {
        if (!session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        gestore.acquistaCarrello(this);
    }

    public void riceviMessaggio(String messaggio) {
        System.out.println("[Messaggio]: " + messaggio);
    }
}
