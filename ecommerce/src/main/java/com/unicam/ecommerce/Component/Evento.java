package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * La classe Evento rappresenta un evento gestito dal sistema eCommerce.
 *
 * Ogni evento ha:
 *  - un nome identificativo,
 *  - un luogo (posizione geografica),
 *  - una data e un orario,
 *  - un identificatore univoco (UUID),
 *  - un numero di biglietti disponibili,
 *  - una descrizione testuale opzionale.
 *
 * La classe supporta la serializzazione/deserializzazione JSON
 * tramite le annotazioni di Jackson.
 */
public class Evento {
    private String nome;                    // Nome dell'evento
    private Posizione luogo;                // Posizione geografica dell'evento

    @JsonFormat(pattern = "yyyy-MM-dd")     // Formato JSON della data
    private LocalDate data;

    @JsonFormat(pattern = "HH:mm:ss")       // Formato JSON dell'orario
    private LocalTime orario;

    private UUID Id;                        // Identificatore univoco dell'evento
    private int biglietti;                  // Numero di biglietti disponibili
    private InformazioniTestuali descrizione; // Informazioni aggiuntive (titolo + testo)

    // ==============================
    // COSTRUTTORI
    // ==============================

    /**
     * Costruttore principale usato da Jackson per deserializzare da JSON.
     */
    @JsonCreator
    public Evento(
            @JsonProperty("nome") String nome,
            @JsonProperty("luogo") Posizione luogo,
            @JsonProperty("data") LocalDate data,
            @JsonProperty("orario") LocalTime orario,
            @JsonProperty("id") UUID id,
            @JsonProperty("biglietti") int biglietti,
            @JsonProperty("descrizione") InformazioniTestuali descrizione
    ) {
        this.nome = nome;
        this.luogo = luogo;
        this.data = data;
        this.orario = orario;
        this.Id = id;
        this.biglietti = biglietti;
        this.descrizione = descrizione;
    }

    /**
     * Costruttore di convenienza per creare eventi senza ID e descrizione,
     * generando automaticamente un UUID.
     */
    public Evento(String nome, Posizione luogo, LocalDate data, LocalTime orario, int biglietti) {
        this(nome, luogo, data, orario, UUID.randomUUID(), biglietti, null);
    }

    // ==============================
    // GETTER e SETTER
    // ==============================
    @JsonProperty("descrizione")
    public InformazioniTestuali getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(InformazioniTestuali descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Posizione getLuogo() {
        return luogo;
    }

    public void setLuogo(Posizione luogo) {
        this.luogo = luogo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }

    public UUID getId() {
        return Id;
    }

    public int getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(int biglietti) {
        this.biglietti = biglietti;
    }
}
