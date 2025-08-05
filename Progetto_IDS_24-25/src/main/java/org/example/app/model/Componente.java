package org.example.app.model;

import org.example.app.controls.GestoreCreazioni;
import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.IGestore;

//rappresenta le classi che si scambiano messaggi all'interno del pattern mediator
public abstract class Componente {
    private String nome, cognome;
    private int matricola;
    private String email;


    public Componente( String nome, String cognome, int matricola, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public abstract void riceviMessaggio(String messaggio);
}
