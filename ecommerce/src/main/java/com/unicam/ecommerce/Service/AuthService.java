package com.unicam.ecommerce.Service;

import com.unicam.ecommerce.Component.Account;
import com.unicam.ecommerce.Component.Marketplace;
import com.unicam.ecommerce.Component.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servizio responsabile della gestione dell'autenticazione degli utenti.
 *
 * Utilizza il pattern Chain of Responsibility attraverso una lista di AuthHandler,
 * in modo da poter concatenare più controlli di autenticazione (es: verifica credenziali,
 * controllo ruolo, controlli aggiuntivi).
 *
 * Funzionalità principali:
 * 1. Costruzione della catena di handler a partire da una lista fornita.
 * 2. Metodo logIn(String username, String password) che effettua:
 *    - Verifica credenziali tramite la catena di handler.
 *    - Recupero dell'account dal marketplace se la verifica ha successo.
 *    - Salvataggio dell'utente nella sessione corrente.
 */
@Service
public class AuthService {
    private final AuthHandler firstHandler;  // Primo handler della catena
    private final Session session;           // Gestione sessione utente
    private final Marketplace marketplace;   // Riferimento al marketplace contenente gli account

    /**
     * Costruttore che costruisce la catena di responsabilità a partire dalla lista di handler.
     * @param handlers lista di AuthHandler
     * @param session sessione utente
     * @param marketplace marketplace dove sono memorizzati gli account
     */
    public AuthService(List<AuthHandler> handlers, Session session, Marketplace marketplace) {
        if (handlers.isEmpty()) throw new IllegalArgumentException("Deve esserci almeno un AuthHandler");
        this.session = session;
        this.marketplace = marketplace;

        // Costruzione della catena di responsabilità
        firstHandler = handlers.get(0);
        AuthHandler current = firstHandler;
        for (int i = 1; i < handlers.size(); i++) {
            current.setNext(handlers.get(i));
            current = handlers.get(i);
        }
    }

    /**
     * Effettua il login di un utente.
     * @param username username dell'utente
     * @param password password dell'utente
     * @return true se il login ha successo, false altrimenti
     */
    public boolean logIn(String username, String password) {
        System.out.println("[AuthService] Tentativo di login per: " + username);

        // Verifica credenziali tramite la catena di handler
        boolean success = firstHandler.check(username, password);

        if (success) {
            // Recupero account dal marketplace
            Account user = marketplace.getProfili().values().stream()
                    .filter(a -> a.getNomeUtente().equals(username))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Account non trovato dopo login"));

            // Salvataggio dell'utente nella sessione
            session.setCurrentUser(user);
            session.setAuthenticated(true);
            System.out.println("[AuthService] Login riuscito, utente in sessione: " + user.getNomeUtente());
        } else {
            System.out.println("[AuthService] Login fallito per utente: " + username);
        }
        return success;
    }
}
