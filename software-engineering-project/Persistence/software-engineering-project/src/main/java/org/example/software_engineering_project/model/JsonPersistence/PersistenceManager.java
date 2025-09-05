package org.example.software_engineering_project.model.JsonPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.software_engineering_project.model.Marketplace;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class PersistenceManager {

    private static final String DIRECTORY_PATH = "Persistence";
    private static final String FILE_PATH = DIRECTORY_PATH + "/MarketPlace_data.json";
    private final ObjectMapper mapper;
    private final Marketplace marketplace;

    public PersistenceManager(Marketplace marketplace) {
        this.mapper = JsonConfig.getMapper();
        this.marketplace = marketplace;
    }

    public void salva() throws IOException {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists() && dir.mkdirs()) {
            System.out.println("Cartella 'Persistence' creata.");
        }

        MarketplaceData data = new MarketplaceData(
                marketplace.getProdotti(),
                marketplace.getPacchetti(),
                marketplace.getEventi(),
                marketplace.getAccount()
        );

        File file = new File(FILE_PATH);
        mapper.writeValue(file, data);
        System.out.println("Dati salvati in " + FILE_PATH);
    }

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
            salva();
        }
    }
}
