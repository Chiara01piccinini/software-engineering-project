package com.unicam.ecommerce.JsonPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicam.ecommerce.Component.Marketplace;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Servizio responsabile della persistenza dei dati del marketplace su file JSON.
 *
 * Funzioni principali:
 * 1. Salvataggio dello stato attuale del marketplace su file JSON.
 * 2. Caricamento dei dati dal file JSON per ripristinare lo stato del marketplace.
 *
 * Utilizza la classe MarketplaceData come DTO per serializzare e deserializzare
 * prodotti, pacchetti, eventi e profili utenti.
 *
 * La classe garantisce anche la creazione della cartella "Persistence" se non esiste.
 * Implementa una logica di inizializzazione con mappe vuote nel caso il file JSON sia assente o vuoto.
 */
@Service
public class PersistenceManager {

    private static final String DIRECTORY_PATH = "Persistence";                  // Cartella dove salvare i file JSON
    private static final String FILE_PATH = DIRECTORY_PATH + "/MarketPlace_data.json"; // Percorso del file JSON
    private final ObjectMapper mapper;                                           // Mapper Jackson per serializzazione/deserializzazione
    private final Marketplace marketplace;                                       // Riferimento al marketplace corrente

    public PersistenceManager(Marketplace marketplace) {
        this.mapper = JsonConfig.getMapper();
        this.marketplace = marketplace;
    }

    /**
     * Salva lo stato corrente del marketplace su file JSON.
     * Crea la cartella "Persistence" se non esiste.
     * @throws IOException in caso di errori di scrittura su file
     */
    public void salva() throws IOException {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists() && dir.mkdirs()) {
            System.out.println("Cartella 'Persistence' creata.");
        }

        MarketplaceData data = new MarketplaceData(
                marketplace.getProdotti(),
                marketplace.getPacchetti(),
                marketplace.getEventi(),
                marketplace.getProfili()
        );

        File file = new File(FILE_PATH);
        mapper.writeValue(file, data);
        System.out.println("Dati salvati in " + FILE_PATH);
    }

    /**
     * Carica i dati dal file JSON e popola il marketplace.
     * Se il file non esiste o è vuoto, inizializza mappe vuote e salva un file nuovo.
     * @throws IOException in caso di errori di lettura dal file
     */
    public void carica() throws IOException {
        File file = new File(FILE_PATH);
        if (file.exists() && file.length() > 0) {
            MarketplaceData data = mapper.readValue(file, MarketplaceData.class);
            marketplace.setProdotti(data.getProdotti());
            marketplace.setPacchetti(data.getPacchetti());
            marketplace.setEventi(data.getEventi());
            marketplace.setAccount(data.getProfili());
            System.out.println("Dati caricati da " + FILE_PATH);
        } else {
            System.out.println("File JSON non ancora esistente o vuoto, inizializzazione nuova.");
            marketplace.setProdotti(new HashMap<>());
            marketplace.setPacchetti(new HashMap<>());
            marketplace.setEventi(new HashMap<>());
            marketplace.setAccount(new HashMap<>());
            salva(); // crea un file JSON iniziale con mappe vuote
        }
    }
}
