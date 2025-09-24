package com.unicam.ecommerce.Controller;

import com.unicam.ecommerce.Component.*;
import com.unicam.ecommerce.Service.AuthService;
import com.unicam.ecommerce.Service.ContentFactory;
import com.unicam.ecommerce.Service.Gestore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller per gestire l'autenticazione e la registrazione degli utenti.
 * Fornisce endpoint per login, logout e registrazione.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;       // Servizio per gestire login/logout
    private final ContentFactory contentFactory; // Factory per creare account e contenuti
    private final Session session;               // Sessione corrente
    private final Gestore gestore;               // Riferimento al gestore

    public AuthController(AuthService authService, ContentFactory contentFactory, Session session, Gestore gestore) {
        this.authService = authService;
        this.contentFactory = contentFactory;
        this.session = session;
        this.gestore = gestore;
    }

    /**
     * Endpoint per il login.
     * Controlla username e password e aggiorna la sessione.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        boolean success = authService.logIn(username, password);
        if (success) {
            return ResponseEntity.ok("Login effettuato con successo!");
        }
        return ResponseEntity.status(401).body("Credenziali non valide");
    }

    /**
     * Endpoint per il logout.
     * Chiude la sessione dell'utente autenticato.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Nessun utente autenticato");
        }
        String user = session.getCurrentUser().getNomeUtente();
        session.logout();
        return ResponseEntity.ok("Logout effettuato per utente: " + user);
    }

    /**
     * Endpoint per la registrazione.
     * Permette di creare account di tipo Venditore, Curatore, Animatore, Distributore, Trasformatore.
     * Possibilità di associare un'azienda e posizione geografica.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> body) {
        String nome = (String) body.get("nome");
        String cognome = (String) body.get("cognome");
        String username = (String) body.get("username");
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String tipo = (String) body.get("tipo");

        // Recupero dati azienda (opzionale)
        Map<String, Object> aziendaMap = (Map<String, Object>) body.get("azienda");
        Azienda azienda = null;
        if (aziendaMap != null) {
            String nomeAzienda = (String) aziendaMap.get("nome");
            String partitaIVA = (String) aziendaMap.get("partitaIVA");
            Map<String, Object> posMap = (Map<String, Object>) aziendaMap.get("posizione");
            Posizione posizione = null;
            if (posMap != null) {
                double latitudine = ((Number) posMap.get("latitudine")).doubleValue();
                double longitudine = ((Number) posMap.get("longitudine")).doubleValue();
                String nomePosizione = (String) posMap.get("nome");
                posizione = new Posizione(nomePosizione, latitudine, longitudine);
            }
            azienda = new Azienda(nomeAzienda, partitaIVA, posizione);
        }

        // Creo il componente in base al tipo di account
        Componente componente;
        switch (TipoAccount.valueOf(tipo)) {
            case DISTRIBUTORE -> componente = new DistributoreDiTipicita(nome, cognome, azienda);
            case TRASFORMATORE -> componente = new Trasformatore(nome, cognome, azienda);
            case PRODUTTORE -> componente = new Produttore(nome, cognome, azienda);
            case ANIMATORE -> componente = new Animatore(nome, cognome, azienda);
            case CURATORE -> componente = new Curatore(nome, cognome);
            case GESTORE -> componente = gestore; // caso speciale: usa l'oggetto gestore esistente
            default -> componente = new Componente(nome, cognome) {};
        }

        // Chiamo la factory per creare l'account
        contentFactory.creaAccount(username, email, password, TipoAccount.valueOf(tipo), componente);
        return ResponseEntity.ok("Account creato per utente: " + username);
    }
}
