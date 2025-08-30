package org.example.app.model.JsonPersistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.app.model.Marketplace;

import java.io.File;
import java.io.IOException;

public class PersistenceManager {
    private static final String filePath = "Persistence/MarketPlace_data.json";
    private final ObjectMapper mapper;

    public PersistenceManager(){
        this.mapper = JsonConfig.getMapper();
    }

    public void salva()throws IOException{
        MarketplaceData data = new MarketplaceData(Marketplace.getProdotti(),Marketplace.getPacchetti(),Marketplace.getEventi(),Marketplace.getAccount());

        File file = new File(filePath);
        mapper.writeValue(file,data);
        System.out.println("Dati salvati in" + filePath);
    }

    public void carica() throws IOException{
        File file = new File(filePath);
        if (file.exists()) {
            MarketplaceData data = mapper.readValue(file, MarketplaceData.class);
            Marketplace.setProdotti(data.getProdotti());
            Marketplace.setPacchetti(data.getPacchetti());
            Marketplace.setEventi(data.getEventi());
            Marketplace.setAccount(data.getProfili());
            System.out.println("Dati caricati da " + filePath);
        } else {
            System.out.println("File JSON non ancora esistente");
        }
    }
}
