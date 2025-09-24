package com.unicam.ecommerce.Component;

import java.util.UUID;

/**
 * Classe astratta che rappresenta una richiesta generica nel sistema.
 * Ogni richiesta ha un ID univoco, un mittente (chi ha generato la richiesta),
 * un nome descrittivo e uno stato di avanzamento.
 */
public abstract class Richiesta {
    private final UUID id;                 // Identificativo univoco della richiesta
    private String nome;                   // Nome o descrizione della richiesta
    private  Componente mittente;    // Componente che ha generato la richiesta (statico per condivisione?)
    private StatoRichiesta stato;          // Stato corrente della richiesta (PENDING, APPROVATA, RIFIUTATA, etc.)

    /**
     * Costruttore principale.
     * Inizializza la richiesta con un ID univoco e imposta lo stato iniziale a PENDING.
     *
     * @param nome     Nome della richiesta
     * @param mittente Componente che genera la richiesta
     */
    public Richiesta(String nome, Componente mittente) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.mittente = mittente;
        this.stato = StatoRichiesta.PENDING;  // tutte le richieste partono come pendenti
    }

    // -------------------------------
    // Getter e setter
    // -------------------------------

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il mittente della richiesta.

     * Potrebbe essere meglio rendere questo campo non statico.
     */
    public  Componente getMittente() {
        return mittente;
    }

    public void setMittente(Componente mittente) {
        this.mittente = mittente;
    }

    public StatoRichiesta getStato() {
        return stato;
    }

    public void setStato(StatoRichiesta stato) {
        this.stato = stato;
    }
}
