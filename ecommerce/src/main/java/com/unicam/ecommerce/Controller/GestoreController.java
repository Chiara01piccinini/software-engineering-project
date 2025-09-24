package com.unicam.ecommerce.Controller;


import com.unicam.ecommerce.Component.Session;
import com.unicam.ecommerce.Service.Gestore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestore")
public class GestoreController {

    private final Gestore gestore;
    private final Session session;

    public GestoreController(Gestore gestore, Session session) {
        this.gestore = gestore;
        this.session = session;
    }

    /**
     * Endpoint per eliminare un account dal marketplace.
     * Solo utenti autenticati come gestore possono eseguire questa operazione.
     *
     * @param username Nome utente dell'account da eliminare
     * @return ResponseEntity con messaggio di successo o errore
     */
    @DeleteMapping("/account")
    public ResponseEntity<String> eliminaAccount(@RequestParam String username) {
        // Controllo autenticazione
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per eliminare account.");
        }

        // Controllo ruolo: solo il gestore può eliminare account
        if (!"GESTORE".equals(session.getCurrentUser().getTipo().name())) {
            return ResponseEntity.status(403).body("Non hai i permessi per eliminare account.");
        }

        boolean eliminato = gestore.eliminaAccount(username);
        if (eliminato) {
            return ResponseEntity.ok("Account '" + username + "' eliminato con successo.");
        } else {
            return ResponseEntity.badRequest().body("Account '" + username + "' non trovato.");
        }
    }
}
