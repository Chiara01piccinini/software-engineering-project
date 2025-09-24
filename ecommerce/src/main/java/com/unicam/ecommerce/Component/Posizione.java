package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) // Indica a Jackson di non serializzare i campi nulli in JSON
public class Posizione {
    private String nome;          // Nome del luogo, es. "Piazza del Duomo"
    private double latitudine;    // Coordinata di latitudine
    private double longitudine;   // Coordinata di longitudine

    /**
     * Costruttore principale utilizzato per la deserializzazione JSON
     * @param nome Nome del luogo
     * @param latitudine Latitudine del luogo
     * @param longitudine Longitudine del luogo
     */
    @JsonCreator
    public Posizione(@JsonProperty("nome") String nome,
                     @JsonProperty("latitudine") double latitudine,
                     @JsonProperty("longitudine") double longitudine) {
        this.nome = nome;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    // -------------------------------
    // Getter e Setter
    // -------------------------------
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }
}
