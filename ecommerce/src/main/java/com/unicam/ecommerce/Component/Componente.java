package com.unicam.ecommerce.Component;

import java.util.ArrayList;

/**
 * Classe astratta che rappresenta un generico componente del sistema e-commerce.
 * Può essere estesa da classi più specifiche (es. Cliente, Venditore, Animatore).
 * Ogni componente ha un nome, un cognome, un codice identificativo,
 * un account associato e può effettuare prenotazioni di eventi.
 */
public abstract class Componente {
    private String nome;
    private String cognome;
    private String codice; // identificativo univoco generato automaticamente
    private static int counter = 0; // contatore statico per generare i codici utenti
    private Account account = null; // account associato al componente
    private ArrayList<String> prenotazioni; // lista delle prenotazioni effettuate

    /**
     * Costruttore principale che inizializza un nuovo componente
     * generando automaticamente un codice univoco.
     */
    public Componente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
        this.prenotazioni = new ArrayList<>();
        counter++;
        this.codice = "Utente" + counter;
    }

    /**
     * Costruttore protetto usato per inizializzare con prenotazioni esistenti.
     */
    protected Componente(ArrayList<String> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    /**
     * Costruttore vuoto (necessario per framework di serializzazione o casi speciali).
     */
    public Componente() { }

    // ===================== GETTER e SETTER =====================

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Componente.counter = counter;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // ===================== METODI OPERATIVI =====================

    /**
     * Aggiunge un elemento al carrello dell'utente associato.
     * @param elemento elemento da aggiungere (es. prodotto o evento)
     * @param quantità quantità desiderata
     */
    public void aggiungiAlCarrello(IElemento elemento, int quantità) {
        this.account.getCarrello().aggiungiElemento(elemento, quantità);
        System.out.println("[Carrello] Aggiunto " + quantità + "x " + elemento.getNome());
    }

    /**
     * Effettua la prenotazione di un evento se ci sono abbastanza biglietti disponibili.
     * @param evento evento da prenotare
     * @param np numero di biglietti richiesti
     */
    public void prenotaEvento(Evento evento, int np) {
        if (evento.getBiglietti() >= np) {
            // Aggiorna il numero di biglietti dell'evento
            evento.setBiglietti(evento.getBiglietti() - np);

            System.out.println("Prenotazione di " + np +
                    " biglietti per l'evento " + evento.getNome() + " avvenuta");

            // Salva i dettagli della prenotazione
            prenotazioni.add(
                    "Prenotazione per " + evento.getNome() +
                            " | Numero biglietti: " + np +
                            " | Data: " + evento.getData() +
                            " | Orario: " + evento.getOrario() +
                            " | Luogo: " + evento.getLuogo().getNome()
            );
        } else {
            System.out.println("Biglietti insufficienti per l'evento " + evento.getNome());
        }
    }
}
