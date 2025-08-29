package org.example.app.model;

public class Animatore extends Componente{
    private Account account;

    public Animatore(int matricola, String email, Account account) {
        super(matricola, email);
        this.account = account;
    }

    public Animatore(int matricola, String email) {
        super(matricola, email);
    }
}
