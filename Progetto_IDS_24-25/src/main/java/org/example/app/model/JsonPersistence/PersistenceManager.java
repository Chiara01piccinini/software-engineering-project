package org.example.app.model.JsonPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.app.model.Marketplace;

import java.io.File;
import java.io.IOException;

public class PersistenceManager {
    private static final String DIRECTORY_PATH = "Persistence";
    private static final String FILE_PATH = DIRECTORY_PATH + "/MarketPlace_data.json";
    private final ObjectMapper mapper;

    public PersistenceManager() {
        this.mapper = JsonConfig.getMapper();
    }

    public void salva() throws IOException {
        // Assicuro che la cartella esista
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) System.out.println("Cartella 'Persistence' creata.");
        }

        MarketplaceData data = new MarketplaceData(
                Marketplace.getProdotti(),
                Marketplace.getPacchetti(),
                Marketplace.getEventi(),
                Marketplace.getAccount()
        );

        File file = new File(FILE_PATH);
        mapper.writeValue(file, data);
        System.out.println("Dati salvati in " + FILE_PATH);
    }

    public void carica() throws IOException {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            MarketplaceData data = mapper.readValue(file, MarketplaceData.class);
            Marketplace.setProdotti(data.getProdotti());
            Marketplace.setPacchetti(data.getPacchetti());
            Marketplace.setEventi(data.getEventi());
            Marketplace.setAccount(data.getProfili());
            System.out.println("Dati caricati da " + FILE_PATH);
        } else {
            System.out.println("File JSON non ancora esistente, inizializzazione nuova.");
        }
    }
}
