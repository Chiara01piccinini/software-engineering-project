package com.unicam.ecommerce.Service;

import com.unicam.ecommerce.Component.Account;
import com.unicam.ecommerce.Component.Marketplace;
import org.springframework.stereotype.Service;

/**
 * Handler specifico della catena di autenticazione (Chain of Responsibility)
 * Questo handler si occupa di controllare l'esistenza dell'account e di loggare
 * il tipo di ruolo dell'utente prima di passare al prossimo handler nella catena.
 */
@Service
public class RoleCheckHandler extends AuthHandler {
    private final Marketplace sistema; // Riferimento al sistema per accedere agli account

    public RoleCheckHandler(Marketplace sistema) {
        this.sistema = sistema;
    }

    /**
     * Controlla se l'utente esiste e stampa il tipo di account.
     * Se l'utente esiste, passa la richiesta al prossimo handler nella catena.
     */
    @Override
    public boolean check(String username, String password) {
        Account account = sistema.getAccountByUsername(username);

        if (account == null) {
            System.out.println("[Auth] Utente non trovato");
            return false; // Fine catena se l'utente non esiste
        }

        System.out.println("[Auth] Tipo account: " + account.getTipo());

        // Passa al prossimo handler nella chain of responsibility
        return super.check(username, password);
    }
}
