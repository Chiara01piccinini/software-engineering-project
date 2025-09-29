package com.unicam.ecommerce.Service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.unicam.ecommerce.Component.Posizione;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Servizio dedicato alla gestione della mappa dei luoghi all'interno del marketplace.
 * Utilizza una HashMap interna per associare una Posizione geografica (latitudine, longitudine)
 * a un nome descrittivo del luogo.
 *
 * Funzionalità principali:
 * - aggiungere nuovi luoghi alla mappa;
 * - recuperare la mappa completa;
 * - stampare la mappa su console per debug/monitoraggio.
 *
 * Nota:
 * La mappa è gestita internamente tramite HashMap, quindi non vi sono duplicati di Posizione.
 * L'aggiunta di un luogo con la stessa Posizione sovrascrive l'eventuale nome precedente.
 */
@Service
public class MappaService {

    // Mappa interna: Posizione -> Nome del luogo
    private final HashMap<Posizione, String> mappa = new HashMap<>();

    /**
     * Restituisce l'intera mappa.
     *
     * @return HashMap con Posizione e nome del luogo
     */
    public HashMap<Posizione, String> getMappa() {
        return mappa;
    }

    /**
     * Aggiunge un nuovo luogo alla mappa.
     * Se la posizione è già presente, il nome del luogo viene sovrascritto.
     *
     * @param posizione Posizione geografica
     * @param nomeLuogo Nome descrittivo del luogo
     */
    public void aggiungiLuogo(Posizione posizione, String nomeLuogo) {
        mappa.put(posizione, nomeLuogo);
    }

    /**
     * Stampa la mappa su console, utile per debug o monitoraggio.
     * Mostra latitudine, longitudine e nome del luogo.
     */
    public void stampaMappa() {
        System.out.println("===== MAPPA LUOGHI =====");
        if (mappa.isEmpty()) {
            System.out.println("Nessun luogo presente nella mappa.");
            return;
        }
        for (Map.Entry<Posizione, String> entry : mappa.entrySet()) {
            Posizione pos = entry.getKey();
            String nomeLuogo = entry.getValue();
            System.out.println(
                    nomeLuogo + " -> Lat: " + pos.getLatitudine() +
                            ", Lon: " + pos.getLongitudine()
            );
        }
    }
}
