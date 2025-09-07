package org.example.software_engineering_project.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
//rappresenta il comportamento del file immagine ,in linea con il pattern strategy
public class FileInformazioniImmagini implements Messaggio  {
    private String pathImmagine;
    private IElemento prodotto;

    public FileInformazioniImmagini(String pathImmagine, IElemento prodotto) {
        this.pathImmagine = pathImmagine;
        this.prodotto = prodotto;
    }

    public String getContenuto() {
        return pathImmagine;
    }
    // legge il file immagine e restituisce il contenuto in Base64
    public String getContenutoBase64() throws IOException {
        File file = new File(pathImmagine);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public String getNome() {
        return prodotto.getNome();
    }

    public IElemento getProdotto() {
        return prodotto;
    }
}
