package com.unicam.ecommerce.Controller;

import com.unicam.ecommerce.Component.*;
import com.unicam.ecommerce.Service.Gestore;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Controller dedicato alle azioni dei venditori all'interno del marketplace.
 * Permette di:
 * - Richiedere prodotti, pacchetti ed eventi
 * - Richiedere aggiunta di informazioni
 * - Attivare la vendita di prodotti
 * - Richiedere sponsorizzazioni
 *
 * Tutte le operazioni richiedono autenticazione e sono limitate ai componenti
 * che corrispondono al tipo corretto (Venditore, Distributore, Trasformatore, Animatore).
 */
@RestController
@RequestMapping("/venditore")
public class VenditoreController {

    private final Session session;   // Gestione della sessione dell'utente
    private final Gestore gestore;   // Servizio per gestire richieste
    private final Marketplace sistema; // Riferimento al marketplace

    public VenditoreController(Session session, Gestore gestore, Marketplace sistema) {
        this.session = session;
        this.gestore = gestore;
        this.sistema = sistema;
    }

    /**
     * Richiesta di aggiunta prodotto al marketplace.
     * Solo utenti di tipo Venditore possono eseguire questa azione.
     */
    @PostMapping("/prodotto")
    public ResponseEntity<String> richiediProdotto(@RequestBody RichiestaProdotto richiesta) {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per fare richieste.");
        }
        Componente componente = session.getCurrentUser().getComponente();
        if (!(componente instanceof Venditore venditore)) {
            return ResponseEntity.status(403).body("Solo i venditori possono fare questa richiesta.");
        }
        venditore.richiediAggiuntaProdotto(richiesta.getNome(), richiesta.getQuantita(), gestore);
        return ResponseEntity.ok("Richiesta di prodotto inviata al gestore.");
    }

    /**
     * Richiesta di aggiunta pacchetto al marketplace.
     * Solo utenti di tipo Distributore possono eseguire questa azione.
     */
    @PostMapping("/pacchetto")
    public ResponseEntity<String> richiediPacchetto(@RequestBody RichiestaPacchetto richiesta) {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per fare richieste.");
        }
        Componente componente = session.getCurrentUser().getComponente();
        if (!(componente instanceof DistributoreDiTipicita distributore)) {
            return ResponseEntity.status(403).body("Solo i distributori possono fare questa richiesta.");
        }
        distributore.richiediAggiuntaPacchetto(
                richiesta.getNome(),
                richiesta.getQuantita(),
                richiesta.getProdotti(),
                richiesta.getPercentualeSconto(),
                gestore
        );
        return ResponseEntity.ok("Richiesta di pacchetto inviata al gestore.");
    }

    /**
     * Richiesta di aggiunta evento al marketplace.
     * Solo utenti di tipo Animatore possono eseguire questa azione.
     */
    @PostMapping("/evento")
    public ResponseEntity<String> richiediEvento(@RequestBody RichiestaEvento richiesta) {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per fare richieste.");
        }
        Componente componente = session.getCurrentUser().getComponente();
        if (!(componente instanceof Animatore animatore)) {
            return ResponseEntity.status(403).body("Solo gli animatori possono fare questa richiesta.");
        }
        animatore.richiediAggiuntaEvento(
                richiesta.getNome(),
                richiesta.getBiglietti(),
                richiesta.getData(),
                richiesta.getOrario(),
                richiesta.getLuogo(),
                gestore
        );
        return ResponseEntity.ok("Richiesta di evento inviata al gestore.");
    }

    /**
     * Richiesta di aggiunta informazioni.
     * Gestisce diversi casi a seconda del tipo di venditore:
     * - Trasformatore: informazioni su prodotti trasformati
     * - Distributore: informazioni su pacchetti
     * - Animatore: informazioni su eventi
     * - Produttore: informazioni su prodotti base
     */
    @PostMapping("/informazioni")
    public ResponseEntity<String> richiediInformazioni(@RequestBody RichiestaInformazioni richiesta) {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per fare richieste.");
        }

        Componente componente = session.getCurrentUser().getComponente();
        if (!(componente instanceof Venditore)) {
            return ResponseEntity.status(403).body("Solo i venditori possono fare questa richiesta.");
        }

        // Caso 1: Trasformatore
        if (componente instanceof Trasformatore trasformatore) {
            // Cerca prodotto trasformato appartenente all'azienda del trasformatore
            Prodotto prodottoTrovato = sistema.getProdotti().values().stream()
                    .filter(p -> p.getNome().equals(richiesta.getNomeProdotto()) &&
                            p.getAzienda().equals(trasformatore.getAzienda()))
                    .findFirst().orElse(null);

            if (prodottoTrovato == null || !(prodottoTrovato instanceof ProdottoTrasformato)) {
                return ResponseEntity.badRequest().body("Prodotto non trovato o non valido per aggiungere informazioni.");
            }
            trasformatore.richiediAggiuntaInformazioni(
                    (ProdottoTrasformato) prodottoTrovato,
                    richiesta.getInfo().getTitolo(),
                    richiesta.getInfo().getDescrizione(),
                    gestore
            );
            return ResponseEntity.ok("Richiesta di informazioni inviata al curatore (Prodotto Trasformato).");
        }

        // Caso 2: Distributore → pacchetti
        if (componente instanceof DistributoreDiTipicita distributore) {
            Pacchetto pacchettoTrovato = sistema.getPacchetti().values().stream()
                    .filter(pac -> pac.getNome().equals(richiesta.getNomeProdotto()))
                    .findFirst().orElse(null);

            if (pacchettoTrovato == null) return ResponseEntity.badRequest().body("Pacchetto non trovato.");

            distributore.richiediAggiuntaInformazioni(
                    pacchettoTrovato,
                    richiesta.getInfo().getTitolo(),
                    richiesta.getInfo().getDescrizione(),
                    gestore
            );
            return ResponseEntity.ok("Richiesta di informazioni inviata al curatore (Pacchetto).");
        }

        // Caso 3: Animatore → eventi
        if (componente instanceof Animatore animatore) {
            Evento eventoTrovato = sistema.getEventi().values().stream()
                    .filter(ev -> ev.getNome().equals(richiesta.getNomeProdotto()))
                    .findFirst().orElse(null);

            if (eventoTrovato == null) return ResponseEntity.badRequest().body("Evento non trovato.");

            animatore.richiediAggiuntaInformazioni(
                    eventoTrovato,
                    richiesta.getInfo().getTitolo(),
                    richiesta.getInfo().getDescrizione(),
                    gestore
            );
            return ResponseEntity.ok("Richiesta di informazioni inviata al curatore (Evento).");
        }

        // Caso 4: Produttore → prodotti base
        if (componente instanceof Produttore produttore) {
            Prodotto prodottoTrovato = sistema.getProdotti().values().stream()
                    .filter(p -> p.getNome().equals(richiesta.getNomeProdotto()) &&
                            p.getAzienda().equals(produttore.getAzienda()))
                    .findFirst().orElse(null);

            if (prodottoTrovato == null) return ResponseEntity.badRequest().body("Prodotto non trovato.");

            produttore.richiediAggiuntaInformazioni(
                    prodottoTrovato,
                    richiesta.getInfo().getTitolo(),
                    richiesta.getInfo().getDescrizione(),
                    gestore
            );
            return ResponseEntity.ok("Richiesta di informazioni inviata al curatore (Prodotto).");
        }

        return ResponseEntity.status(403).body("Non hai i permessi per aggiungere informazioni.");
    }

    /**
     * Attiva la vendita di un prodotto impostando il prezzo.
     * Solo i venditori possono eseguire questa azione.
     */
    @PostMapping("/attiva-vendita")
    public ResponseEntity<String> attivaVendita(@RequestBody Map<String, Object> body) {
        if (!session.isAuthenticated()) return ResponseEntity.status(403).body("Devi essere loggato.");
        Componente componente = session.getCurrentUser().getComponente();
        if (!(componente instanceof Venditore venditore)) {
            return ResponseEntity.status(403).body("Solo i venditori possono attivare la vendita dei prodotti.");
        }

        String nomeProdotto = (String) body.get("nomeProdotto");
        BigDecimal prezzo;
        try {
            prezzo = new BigDecimal(body.get("prezzo").toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Prezzo non valido.");
        }

        Prodotto prodotto = sistema.getProdotti().values().stream()
                .filter(p -> p.getNome().equals(nomeProdotto))
                .findFirst().orElse(null);

        if (prodotto == null) return ResponseEntity.badRequest().body("Prodotto non trovato.");

        boolean attivata = venditore.attivaLaVendita(prodotto, prezzo);
        return attivata
                ? ResponseEntity.ok("Vendita attivata per il prodotto: " + prodotto.getNome())
                : ResponseEntity.badRequest().body("Prezzo non valido o prodotto nullo.");
    }

    /**
     * Richiesta di sponsorizzazione.
     * Solo i venditori possono inviare richieste di sponsorizzazione.
     */
    @PostMapping("/sponsorizza")
    public ResponseEntity<String> richiediSponsorizzazione(@RequestBody RichiestaSponsorizzazione richiesta) {
        if (!session.isAuthenticated()) return ResponseEntity.status(403).body("Devi essere loggato per fare richieste.");
        Componente componente = session.getCurrentUser().getComponente();
        if (!(componente instanceof Venditore venditore)) {
            return ResponseEntity.status(403).body("Solo i venditori possono fare questa richiesta.");
        }
        venditore.richiediSponsorizzazione(gestore, richiesta, richiesta.getPiattaforme());
        return ResponseEntity.ok("Richiesta di sponsorizzazione inviata con successo.");
    }
}
