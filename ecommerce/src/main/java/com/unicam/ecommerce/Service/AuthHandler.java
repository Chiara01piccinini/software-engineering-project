package com.unicam.ecommerce.Service;

import org.springframework.stereotype.Service;

/**
 * Classe astratta che implementa il pattern Chain of Responsibility per la gestione
 * dell'autenticazione degli utenti.
 *
 * Ogni handler può verificare le credenziali in modo specifico e, se non è in grado
 * di gestirle, passa la richiesta al prossimo handler nella catena.
 *
 * Funzionalità principali:
 * 1. `setNext(AuthHandler next)` → imposta il prossimo handler nella catena.
 * 2. `check(String username, String password)` → verifica le credenziali; se l'handler
 *    corrente non le gestisce, passa la richiesta al prossimo handler.
 *
 * Questo approccio permette di concatenare più controlli di autenticazione modulari,
 * ad esempio verifica di username/password, verifica del ruolo dell'utente, ecc.
 */
@Service
public abstract class AuthHandler {
    private AuthHandler next; // Riferimento al prossimo handler nella catena

    /**
     * Imposta il prossimo handler nella catena.
     * @param next handler successivo
     */
    public void setNext(AuthHandler next) {
        this.next = next;
    }

    /**
     * Controlla le credenziali.
     * Se l'handler corrente non gestisce il controllo, passa la richiesta al prossimo handler.
     * @param username username da verificare
     * @param password password da verificare
     * @return true se la verifica va a buon fine, false altrimenti
     */
    public boolean check(String username, String password) {
        if (next != null) {
            return next.check(username, password);
        }
        return true; // Se non ci sono altri handler nella catena, ritorna true
    }
}
