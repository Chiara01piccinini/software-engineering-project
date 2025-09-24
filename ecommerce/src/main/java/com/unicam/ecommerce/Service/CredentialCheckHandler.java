package com.unicam.ecommerce.Service;

import com.unicam.ecommerce.Component.Account;
import com.unicam.ecommerce.Component.Marketplace;
import com.unicam.ecommerce.Component.Session;
import org.springframework.stereotype.Service;

/**
 * CredentialCheckHandler è un handler concreto nel pattern Chain of Responsibility
 * per la gestione dell'autenticazione degli utenti.
 *
 * Compiti principali:
 * 1. Controlla se l'account con lo username fornito esiste nel marketplace.
 * 2. Verifica che la password corrisponda a quella registrata.
 * 3. In caso di successo, imposta l'utente nella sessione corrente.
 * 4. Se le credenziali sono corrette, passa la richiesta al prossimo handler della catena.
 *
 * Questo handler fa parte di una catena di controlli (AuthHandler) che possono includere
 * altri step di autenticazione, come verifica del ruolo, blocchi, ecc.
 */
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
        // Recupera l'account dal marketplace
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

        // Passa il controllo al prossimo handler nella catena
        return super.check(username, password);
    }
}
