package com.unicam.ecommerce.Controller;

import com.unicam.ecommerce.Component.Componente;
import com.unicam.ecommerce.Component.IElemento;
import com.unicam.ecommerce.Component.Marketplace;
import com.unicam.ecommerce.Component.Session;
import com.unicam.ecommerce.JsonPersistence.PersistenceManager;
import com.unicam.ecommerce.Service.Gestore;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.UUID;

/**
 * Controller REST per gestire tutte le operazioni sul carrello degli utenti.
 * Permette di aggiungere/rimuovere elementi, visualizzare il carrello e acquistare tutto.
 */
@RestController
@RequestMapping("/carrello")
public class CarrelloController {

    private final Gestore gestore;        // Servizio per gestire acquisti e logica del marketplace
    private final Marketplace sistema;    // Riferimento al marketplace (prodotti, pacchetti)
    private final Session session;        // Gestione sessione utente
    private final PersistenceManager persistenceManager;

    public CarrelloController(PersistenceManager persistenceManager,Gestore gestore, Marketplace sistema, Session session) {
        this.gestore = gestore;
        this.sistema = sistema;
        this.session = session;
        this.persistenceManager= persistenceManager;
    }

    /**
     * Aggiunge un prodotto o pacchetto al carrello.
     * @param id id dell'elemento (prodotto o pacchetto)
     * @param quantita quantità da aggiungere
     */
    @PostMapping("/aggiungi/{id}/{quantita}")
    public ResponseEntity<String> aggiungiAlCarrello(
            @PathVariable UUID id,
            @PathVariable int quantita
    ) throws IOException {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per aggiungere al carrello.");
        }
        //persistenceManager.carica();
        Componente componente = session.getCurrentUser().getComponente();
        IElemento p = sistema.getElementoById(id);
        // Aggiunge l'elemento al carrello e restituisce true/false se l'operazione è riuscita
        boolean risultato = componente.getAccount().getCarrello().aggiungiElemento(p, quantita);
        persistenceManager.salva();
        return risultato
                ? ResponseEntity.ok("Prodotto aggiunto al carrello.")
                : ResponseEntity.badRequest().body("Errore nell'aggiunta al carrello.");
    }

    /**
     * Rimuove un elemento dal carrello
     * @param id id dell'elemento da rimuovere
     */
    @DeleteMapping("/rimuovi/{id}")
    public ResponseEntity<String> rimuoviDalCarrello(@PathVariable UUID id) throws IOException {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per rimuovere dal carrello.");
        }
        persistenceManager.carica();
        Componente componente = session.getCurrentUser().getComponente();
        boolean risultato = componente.getAccount().getCarrello().rimuoviProdotto(sistema.getElementoById(id));

        if (risultato){
            persistenceManager.salva();
            return ResponseEntity.ok("Elemento rimosso dal carrello.");
        }else return ResponseEntity.badRequest().body("Elemento non trovato nel carrello.");

    }

    /**
     * Visualizza tutti gli elementi presenti nel carrello
     */
    @GetMapping("/visualizza")
    public ResponseEntity<?> visualizzaCarrello() throws IOException {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per visualizzare il carrello.");
        }
        persistenceManager.carica();
        Componente componente = session.getCurrentUser().getComponente();
        return ResponseEntity.ok(componente.getAccount().getCarrello().getProdotti());
    }

    /**
     * Effettua l'acquisto di tutti gli elementi presenti nel carrello.
     * Si affida al servizio Gestore per la logica di acquisto e decremento quantità.
     */
    @PostMapping("/acquista")
    public ResponseEntity<String> acquistaCarrello() throws IOException {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per effettuare un acquisto.");
        }
        Componente componente = session.getCurrentUser().getComponente();
        gestore.acquistaCarrello(componente);  // logica di acquisto centralizzata
        return ResponseEntity.ok("Acquisto completato");
    }
}
