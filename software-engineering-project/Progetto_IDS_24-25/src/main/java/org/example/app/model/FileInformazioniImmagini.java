package org.example.app.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
//rappresenta il comportamento del file immagine ,in linea con il pattern strategy
public class FileInformazioniImmagini implements IFileInformazioni  {
    private String pathImmagine;

    public FileInformazioniImmagini(String pathImmagine, int idRichiesta) {
        this.pathImmagine = pathImmagine;
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
}
