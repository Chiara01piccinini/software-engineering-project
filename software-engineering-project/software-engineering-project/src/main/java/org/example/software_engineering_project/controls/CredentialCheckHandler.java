package org.example.software_engineering_project.controls;

import org.example.software_engineering_project.model.Account;
import org.example.software_engineering_project.model.Marketplace;
import org.springframework.stereotype.Service;

@Service
public class CredentialCheckHandler extends AuthHandler {
    private final Marketplace marketplace;
    private final Session session;

    public CredentialCheckHandler(Marketplace marketplace, Session session) {
        this.marketplace = marketplace;
        this.session = session;
    }

    @Override
    public boolean check(String username, String password) {
        Account account = marketplace.getAccountByUsername(username);

        if (account == null) {
            System.out.println("[Auth] Utente non trovato");
            return false;
        }

        if (!account.getPassword().equals(password)) {
            System.out.println("[Auth] Password errata");
            return false;
        }

        System.out.println("[Auth] Credenziali corrette");
        session.setCurrentUser(account);

        return super.check(username, password); // passa al prossimo handler
    }
}
