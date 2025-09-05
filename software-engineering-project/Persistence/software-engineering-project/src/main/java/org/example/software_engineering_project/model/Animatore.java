package org.example.software_engineering_project.model;

import org.example.software_engineering_project.controls.Session;

public class Animatore extends Componente{
    private Account account;

    public Animatore(int matricola, String email, Marketplace sistema, Session session, Account account) {
        super(matricola, email, sistema, session);
        this.account = account;
    }
}
