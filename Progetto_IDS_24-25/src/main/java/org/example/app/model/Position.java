package org.example.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    private String nome;
    private double latitudine;
    private double longitudine;

    @JsonCreator
    public Position(@JsonProperty("nome") String nome,@JsonProperty("latitudine") double latitudine,@JsonProperty("longitudine") double longitudine) {
        this.nome = nome;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

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

    @Override
    public String toString() {
        return "Position{" +
                "latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                '}';
    }
}

