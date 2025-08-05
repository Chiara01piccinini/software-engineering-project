package org.example.app.model;

import org.example.app.controls.IGestore;

public class Animatore extends Componente{
    public Animatore(String nome, String cognome, int matricola, String email) {
        super(nome, cognome, matricola, email);
    }

    @Override
    public void riceviMessaggio(String messaggio) {System.out.println("[Messaggio]: " + messaggio);}
}
