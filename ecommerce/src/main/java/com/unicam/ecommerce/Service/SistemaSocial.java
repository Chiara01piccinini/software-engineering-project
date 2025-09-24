package com.unicam.ecommerce.Service;

import com.unicam.ecommerce.Component.Piattaforme;
import com.unicam.ecommerce.Component.Richiesta;
import org.springframework.stereotype.Service;

/**
 * Servizio che simula la pubblicazione di contenuti sponsorizzati
 * su diverse piattaforme social.
 *
 * Questa classe rappresenta un'integrazione semplificata con i social network:
 * - Non vi è alcuna connessione reale con piattaforme esterne;
 * - Si limita a stampare a console un messaggio che indica la pubblicazione
 *   della sponsorizzazione per una determinata richiesta.
 *
 * Viene utilizzata principalmente dal Gestore per gestire le richieste
 * di sponsorizzazione avanzate dai venditori.
 */
@Service
public class SistemaSocial {

    /**
     * Simula la pubblicazione di un contenuto sponsorizzato.
     *
     * @param s          Richiesta di sponsorizzazione (contiene i dati del contenuto)
     * @param piattaforma Piattaforma social sulla quale pubblicare il contenuto
     */
    public void pubblica(Richiesta s, Piattaforme piattaforma) {
        System.out.println("[SistemaSponsorizzazioni] Sponsorizzazione pubblicata per: "
                + s.getNome() + ", piattaforma: " + piattaforma);
    }
}

