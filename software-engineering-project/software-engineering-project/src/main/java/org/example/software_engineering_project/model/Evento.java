package org.example.software_engineering_project.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Evento {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime orario;

    private Position luogo;
    private String descrizione;
    private final UUID id;
    private String nome;
    private int biglietti;

    @JsonCreator
    public Evento(
            @JsonProperty("data") LocalDate data,
            @JsonProperty("orario") LocalTime orario,
            @JsonProperty("luogo") Position luogo,
            @JsonProperty("nome") String nome,
            @JsonProperty("biglietti") int biglietti,
            @JsonProperty("id") UUID id,
            @JsonProperty("descrizione") String descrizione
    ) {
        this.data = data;
        this.orario = orario;
        this.luogo = luogo;
        this.nome = nome;
        this.biglietti = biglietti;
        this.id = id;
        this.descrizione = descrizione;
    }

    public Evento(LocalDate data, LocalTime orario, Position luogo, String nome, int biglietti, String descrizione) {
        this(data, orario, luogo, nome, biglietti, UUID.randomUUID(), descrizione);
    }

    // Getters e Setters

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

    public Position getLuogo() {
        return luogo;
    }

    public void setLuogo(Position luogo) {
        this.luogo = luogo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public UUID getID() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(int biglietti) {
        this.biglietti = biglietti;
    }

    public void rimuoviBiglietto(int prenotazione) {
        if (prenotazione > 0 && prenotazione <= this.biglietti) {
            this.biglietti -= prenotazione;
        }
    }
}

