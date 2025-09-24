package com.unicam.ecommerce.Controller;

import com.unicam.ecommerce.Component.*;
import com.unicam.ecommerce.Service.Gestore;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Controller REST per la gestione delle richieste da parte di un Curatore.
 * Permette di visualizzare richieste pendenti, approvare prodotti, pacchetti, eventi o informazioni aggiuntive.
 */
@RestController
@RequestMapping("/curatore")
public class CuratoreController {

    private final Session session;     // Gestione sessione utente
    private final Curatore curatore;   // Curatore che gestisce le richieste
    private final Gestore gestore;     // Servizio per applicare approvazioni/rifiuti nel sistema

    public CuratoreController(Session session, Curatore curatore, Gestore gestore) {
        this.session = session;
        this.curatore = curatore;
        this.gestore = gestore;
    }

    /**
     * Visualizza tutte le richieste pendenti
     */
    @GetMapping("/richieste-pendenti")
    public ResponseEntity<List<String>> visualizzaRichiestePendenti() {
        if (!session.isAuthenticated()) { // Controllo autenticazione
            return ResponseEntity.status(403).body(null);
        }

        Componente componente = session.getCurrentUser().getComponente();
        if (!(componente instanceof Curatore)) { // Controllo ruolo
            return ResponseEntity.status(403).body(null);
        }

        List<String> lista = curatore.visualizzaRichiestePendenti(); // Lista di richieste pendenti
        return ResponseEntity.ok(lista);
    }

    /**
     * Approva una richiesta di prodotto tramite ID
     */
    @PostMapping("/approva-prodotto/{id}")
    public ResponseEntity<String> approvaProdotto(@PathVariable String id) {
        if (!session.isAuthenticated()) return ResponseEntity.status(403).body("Devi essere loggato.");
        if (!(session.getCurrentUser().getComponente() instanceof Curatore))
            return ResponseEntity.status(403).body("Solo i curatori possono approvare richieste.");

        RichiestaProdotto richiestaDaGestire = null;
        for (Richiesta r : curatore.getRichiestePendenti()) {
            if (r instanceof RichiestaProdotto rp && rp.getId().toString().equals(id)) {
                richiestaDaGestire = rp;
                break;
            }
        }

        if (richiestaDaGestire == null) {
            return ResponseEntity.badRequest().body("Richiesta non trovata o già gestita.");
        }

        curatore.approvaProdotto(richiestaDaGestire, gestore);
        return ResponseEntity.ok("Prodotto gestito dal curatore.");
    }

    /**
     * Approva una richiesta di pacchetto tramite ID
     */
    @PostMapping("/approva-pacchetto/{id}")
    public ResponseEntity<String> approvaPacchetto(@PathVariable String id) {
        if (!session.isAuthenticated()) return ResponseEntity.status(403).body("Devi essere loggato.");
        if (!(session.getCurrentUser().getComponente() instanceof Curatore))
            return ResponseEntity.status(403).body("Solo i curatori possono approvare richieste.");

        RichiestaPacchetto richiestaDaGestire = null;
        for (Richiesta r : curatore.getRichiestePendenti()) {
            if (r instanceof RichiestaPacchetto rp && rp.getId().toString().equals(id)) {
                richiestaDaGestire = rp;
                break;
            }
        }

        if (richiestaDaGestire == null) return ResponseEntity.badRequest().body("Richiesta non trovata o già gestita.");

        curatore.approvaPacchetto(richiestaDaGestire, gestore);
        return ResponseEntity.ok("Pacchetto gestito dal curatore.");
    }

    /**
     * Approva una richiesta di evento tramite ID
     */
    @PostMapping("/approva-evento/{id}")
    public ResponseEntity<String> approvaEvento(@PathVariable String id) {
        if (!session.isAuthenticated()) return ResponseEntity.status(403).body("Devi essere loggato.");
        if (!(session.getCurrentUser().getComponente() instanceof Curatore))
            return ResponseEntity.status(403).body("Solo i curatori possono approvare richieste.");

        RichiestaEvento richiestaDaGestire = null;
        for (Richiesta r : curatore.getRichiestePendenti()) {
            if (r instanceof RichiestaEvento re && re.getId().toString().equals(id)) {
                richiestaDaGestire = re;
                break;
            }
        }

        if (richiestaDaGestire == null) return ResponseEntity.badRequest().body("Richiesta non trovata o già gestita.");

        curatore.approvaEvento(richiestaDaGestire, gestore);
        return ResponseEntity.ok("Evento gestito dal curatore.");
    }

    /**
     * Approva una richiesta di informazioni tramite ID
     */
    @PostMapping("/approva-informazioni/{id}")
    public ResponseEntity<String> approvaInformazioni(@PathVariable String id) {
        if (!session.isAuthenticated()) return ResponseEntity.status(403).body("Devi essere loggato.");
        if (!(session.getCurrentUser().getComponente() instanceof Curatore))
            return ResponseEntity.status(403).body("Solo i curatori possono approvare richieste.");

        RichiestaInformazioni richiestaDaGestire = null;
        for (Richiesta r : curatore.getRichiestePendenti()) {
            if (r instanceof RichiestaInformazioni ri && ri.getId().toString().equals(id)) {
                richiestaDaGestire = ri;
                break;
            }
        }

        if (richiestaDaGestire == null) return ResponseEntity.badRequest().body("Richiesta non trovata o già gestita.");

        curatore.approvaRichiestaInformazioni(richiestaDaGestire, gestore);
        return ResponseEntity.ok("Informazioni approvate dal curatore.");
    }

    /**
     * Rifiuta una richiesta di informazioni tramite ID
     */
    @PostMapping("/rifiuta-informazioni/{id}")
    public ResponseEntity<String> rifiutaInformazioni(@PathVariable String id) {
        if (!session.isAuthenticated()) return ResponseEntity.status(403).body("Devi essere loggato.");
        if (!(session.getCurrentUser().getComponente() instanceof Curatore))
            return ResponseEntity.status(403).body("Solo i curatori possono rifiutare richieste.");

        RichiestaInformazioni richiestaDaGestire = null;
        for (Richiesta r : curatore.getRichiestePendenti()) {
            if (r instanceof RichiestaInformazioni ri && ri.getId().toString().equals(id)) {
                richiestaDaGestire = ri;
                break;
            }
        }

        if (richiestaDaGestire == null) return ResponseEntity.badRequest().body("Richiesta non trovata o già gestita.");

        curatore.rifiutaRichiestaInformazioni(richiestaDaGestire, gestore);
        return ResponseEntity.ok("Informazioni rifiutate dal curatore.");
    }
}
