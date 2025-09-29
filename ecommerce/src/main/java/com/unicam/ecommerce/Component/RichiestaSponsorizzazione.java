package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe che rappresenta una richiesta di sponsorizzazione all'interno del sistema.
 * Estende la classe astratta Richiesta.
 * Contiene informazioni aggiuntive specifiche per la sponsorizzazione:
 *  - Nome dell'iniziativa
 *  - Descrizione della sponsorizzazione
 *  - Piattaforme su cui la sponsorizzazione dovrà essere effettuata
 */
public class RichiestaSponsorizzazione extends Richiesta {

    private String Nome;               // Nome della sponsorizzazione
    private String descrizione;        // Descrizione dettagliata
    private Piattaforme piattaforme;   // Piattaforme di pubblicazione

    /**
     * Costruttore principale.
     *
     * @param nome          Identificativo della richiesta
     * @param mittente      Componente che crea la richiesta
     * @param nome1         Nome della sponsorizzazione
     * @param descrizione   Descrizione della sponsorizzazione
     * @param piattaforme   Piattaforme su cui pubblicizzare
     */
    public RichiestaSponsorizzazione(String nome, Componente mittente, String nome1, String descrizione, Piattaforme piattaforme) {
        super(nome, mittente);
        Nome = nome1;
        this.descrizione = descrizione;
        this.piattaforme = piattaforme;
    }

    // -------------------------------
    // Getter e setter
    // -------------------------------
    @Override
    public String getNome() {
        return Nome;
    }

    @Override
    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Piattaforme getPiattaforme() {
        return piattaforme;
    }

    public void setPiattaforme(Piattaforme piattaforme) {
        this.piattaforme = piattaforme;
    }
}
