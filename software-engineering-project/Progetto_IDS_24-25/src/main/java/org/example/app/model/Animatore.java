package org.example.app.model;

import org.example.app.controls.IGestore;

public class Animatore extends Componente{
    public Animatore(String nome, String cognome, int matricola, String email, IGestore gestore, String nome1, String cognome1, int matricola1) {
        super(nome, cognome, matricola, email, gestore);
    }

    @Override
    public void riceviMessaggio(String messaggio) {System.out.println("[Messaggio]: " + messaggio);}
}
