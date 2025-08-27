package org.example.app.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Evento  {
    private Date data;
    private LocalDateTime orario;
    private Position luogo;
    private String descrizione;
    private final UUID ID;
    private String nome;
    private int biglietti;

    @JsonCreator
    public Evento(@JsonProperty("data") Date data,@JsonProperty("orario") LocalDateTime orario,@JsonProperty("luogo") Position luogo,@JsonProperty("nome") String nome,@JsonProperty("biglietti") int biglietti,@JsonProperty("id") UUID id,@JsonProperty("descrizione") String descrizione) {
        this.data = data;
        this.orario = orario;
        this.luogo = luogo;
        this.nome=nome;
        this.biglietti=biglietti;
        this.ID = id;
        this.descrizione = descrizione;
    }

    public Evento(Date data, LocalDateTime orario, Position luogo, String nome, int biglietti,String descrizione){
        this(data,orario,luogo,nome,biglietti,UUID.randomUUID(),descrizione);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getID() {
        return ID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public LocalDateTime getOrario() {
        return orario;
    }

    public void setOrario(LocalDateTime orario) {
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

    public int getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(int biglietti) {
        this.biglietti = biglietti;
    }

    public void rimuoviBiglietto(int prenotazione){
       setBiglietti(this.biglietti - prenotazione);
    }
}

