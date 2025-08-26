package org.example.app.controls;

import org.example.app.model.Account;
import org.example.app.model.Marketplace;

public class CredentialCheckHandler extends AuthHandler {
    private Marketplace marketplace;

    public CredentialCheckHandler(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    @Override
    public boolean check(String username, String password) {
        Account account = marketplace.getAccount().get(username);

        if (account == null) {
            System.out.println("[Auth] Utente non trovato");
            return false;
        }
        if (!account.getPassword().equals(password)) {
            System.out.println("[Auth] Password errata");
            return false;
        }

        System.out.println("[Auth] Credenziali corrette");
        // Se è l’ultimo passo con successo, salvo la sessione
        Session.setCurrentUser(account);

        return checkNext(username, password);
    }
}


