package com.unicam.ecommerce.Component;

import org.springframework.stereotype.Component;

/**
 * Classe che gestisce la sessione dell'utente all'interno del marketplace.
 * Permette di tenere traccia dell'utente corrente e dello stato di autenticazione.
 */
@Component
public class Session {

    private Account currentUser;   // Utente attualmente loggato
    private boolean authenticated = false;  // Stato di autenticazione

    /**
     * Restituisce l'account dell'utente corrente.
     * @return Account corrente
     */
    public Account getCurrentUser() {
        return currentUser;
    }

    /**
     * Imposta l'utente corrente e aggiorna lo stato di autenticazione.
     * @param currentUser Account da impostare come utente corrente
     */
    public void setCurrentUser(Account currentUser) {
        this.currentUser = currentUser;
        this.authenticated = currentUser != null;
    }

    /**
     * Verifica se la sessione è autenticata.
     * @return true se un utente è loggato, false altrimenti
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Imposta manualmente lo stato di autenticazione.
     * @param authenticated Stato di autenticazione da impostare
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * Esegue il logout dell'utente corrente, resettando sessione e autenticazione.
     */
    public void logout() {
        this.currentUser = null;
        this.authenticated = false;
    }
}
