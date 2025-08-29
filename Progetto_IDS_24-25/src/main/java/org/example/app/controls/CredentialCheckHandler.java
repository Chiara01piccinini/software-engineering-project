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
        Account account = null;

        // Cerco l'account per username
        for (Account acc : Marketplace.getAccount().values()) {
            if (acc.getNomeUtente().equals(username)) {
                account = acc;
                break;
            }
        }

        if (account == null) {
            System.out.println("[Auth] Utente non trovato");
            return false;
        }

        if (!account.getPassword().equals(password)) {
            System.out.println("[Auth] Password errata");
            return false;
        }

        System.out.println("[Auth] Credenziali corrette");

        // Salvo l'account nella sessione
        Session.setCurrentUser(account);

        return checkNext(username, password);
    }

}



