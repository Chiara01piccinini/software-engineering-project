package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * La classe Azienda rappresenta un'entità commerciale registrata nel sistema.
 * Contiene le informazioni principali: nome, partita IVA e posizione geografica.
 */
public class Azienda {
    // Attributi principali dell'azienda
    private String nome;          // Nome dell'azienda
    private String partitaIVA;    // Partita IVA univoca che identifica l'azienda
    private Posizione posizione;  // Posizione geografica dell'azienda

    /**
     * Costruttore usato da Jackson per ricostruire un oggetto Azienda da JSON.
     * @param name nome dell'azienda
     * @param PI partita IVA
     * @param position posizione geografica
     */
    @JsonCreator
    public Azienda(
            @JsonProperty("nome") String name,
            @JsonProperty("PI") String PI,
            @JsonProperty("posizione") Posizione position
    ) {
        this.nome = name;
        this.posizione = position;
        this.partitaIVA = PI;
    }

    /**
     * Costruttore vuoto
     */
    public Azienda() {
    }

    // Getter e Setter standard

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartitaIVA() {
        return partitaIVA;
    }

    public void setPartitaIVA(String partitaIVA) {
        this.partitaIVA = partitaIVA;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }
}
