package com.unicam.ecommerce.Controller;

import com.unicam.ecommerce.Component.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

/**
 * Controller dedicato alle azioni di un utente generico sul marketplace.
 * Fornisce endpoint per:
 * - Visualizzare prodotti e pacchetti disponibili
 * - Visualizzare eventi
 * - Visualizzare la mappa dei luoghi
 * - Prenotare eventi
 *
 * Questo controller è pensato per utenti autenticati e permette
 * di ottenere informazioni aggregate sul sistema senza modificare dati sensibili.
 */
@RestController
@RequestMapping("/utente")
public class UtenteController {

    private final Marketplace marketplace; // Riferimento al sistema principale
    private final Session session;         // Gestione sessione utente

    public UtenteController(Marketplace marketplace, Session session) {
        this.marketplace = marketplace;
        this.session = session;
    }

    /**
     * Endpoint per visualizzare tutti i prodotti singoli e i pacchetti.
     * Restituisce una lista di mappe contenenti informazioni dettagliate:
     * tipo, id, nome, prezzo, quantità, descrizione, azienda, e per i pacchetti
     * la lista dei prodotti inclusi e l'eventuale sconto.
     */
    @GetMapping("/prodotti")
    public List<Map<String, Object>> visualizzaProdotti() {
        List<Map<String, Object>> lista = new ArrayList<>();

        // Prodotti singoli
        for (Prodotto p : marketplace.getProdotti().values()) {
            Map<String, Object> info = new HashMap<>();
            info.put("tipo", "prodotto");
            info.put("id", p.getId());
            info.put("nome", p.getNome());
            info.put("prezzo", p.calcolaPrezzo());
            info.put("quantita", p.getQuantita());
            info.put("descrizione", p.getDescrizione() != null ? p.getDescrizione() : "");
            info.put("azienda", p.getAzienda() != null ? p.getAzienda().getNome() : "N/A");
            lista.add(info);
        }

        // Pacchetti
        for (Pacchetto pac : marketplace.getPacchetti().values()) {
            Map<String, Object> info = new HashMap<>();
            info.put("tipo", "pacchetto");
            info.put("id", pac.getId());
            info.put("nome", pac.getNome());
            info.put("prezzo", pac.calcolaPrezzo());
            info.put("quantita", pac.getQuantita());
            info.put("descrizione", pac.getDescrizione() != null ? pac.getDescrizione() : "");

            // Prodotti inclusi nel pacchetto
            List<Map<String, Object>> prodottiInclusi = new ArrayList<>();
            for (Map.Entry<Integer, Prodotto> entry : pac.getProdotti().entrySet()) {
                Prodotto p = entry.getValue();
                Map<String, Object> pInfo = new HashMap<>();
                pInfo.put("id", p.getId());
                pInfo.put("nome", p.getNome());
                pInfo.put("quantita", p.getQuantita());
                prodottiInclusi.add(pInfo);
            }
            info.put("prodottiInclusi", prodottiInclusi);
            info.put("sconto", pac.getPercentualeSconto() != null ? pac.getPercentualeSconto() : 0);
            lista.add(info);
        }
        return lista;
    }

    /**
     * Endpoint per visualizzare tutti gli eventi disponibili.
     * Restituisce informazioni come id, nome, biglietti disponibili, descrizione,
     * data, ora e posizione dell'evento.
     */
    @GetMapping("/eventi")
    public List<Map<String, Object>> visualizzaEventi() {
        List<Map<String, Object>> lista = new ArrayList<>();
        for (Evento p : marketplace.getEventi().values()) {
            Map<String, Object> info = new HashMap<>();
            info.put("tipo", "prodotto"); // potrebbe essere modificato in "evento" per chiarezza
            info.put("id", p.getId());
            info.put("nome", p.getNome());
            info.put("quantita", p.getBiglietti());
            info.put("descrizione", p.getDescrizione() != null ? p.getDescrizione() : "");
            info.put("ora", p.getOrario());
            info.put("data", p.getData());
            info.put("luogo", p.getLuogo());
            lista.add(info);
        }
        return lista;
    }

    /**
     * Endpoint per visualizzare la mappa dei luoghi disponibili.
     * Restituisce una lista di mappe con nome del luogo, latitudine e longitudine.
     */
    @GetMapping("/mappa")
    public ResponseEntity<?> visualizzaMappa() {
        if (marketplace.getMappa().getMappa().isEmpty()) {
            return ResponseEntity.ok("Nessun luogo presente nella mappa.");
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Posizione, String> entry : marketplace.getMappa().getMappa().entrySet()) {
            Posizione pos = entry.getKey();
            String nomeLuogo = entry.getValue();

            Map<String, Object> luogo = new HashMap<>();
            luogo.put("nome", nomeLuogo);
            luogo.put("latitudine", pos.getLatitudine());
            luogo.put("longitudine", pos.getLongitudine());

            result.add(luogo);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint per prenotare biglietti per un evento specifico.
     * Controlla autenticazione e disponibilità biglietti prima di effettuare
     * la prenotazione tramite il componente dell'utente loggato.
     */
    @PostMapping("/prenota")
    public ResponseEntity<String> prenotaEvento(
            @RequestParam UUID eventoId,
            @RequestParam int biglietti
    ) {
        if (!session.isAuthenticated()) {
            return ResponseEntity.status(403).body("Devi essere loggato per prenotare un evento.");
        }
        Componente componente = session.getCurrentUser().getComponente();
        Evento evento = marketplace.getEventoById(eventoId);
        if (evento == null) {
            return ResponseEntity.badRequest().body("Evento non trovato.");
        }
        if (evento.getBiglietti() < biglietti) {
            return ResponseEntity.badRequest().body("Biglietti insufficienti per l'evento " + evento.getNome());
        }
        componente.prenotaEvento(evento, biglietti);
        return ResponseEntity.ok(
                "Prenotazione avvenuta con successo per l'evento " +
                        evento.getNome() + " (" + biglietti + " biglietti)"
        );
    }
}
