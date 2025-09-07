package org.example.software_engineering_project.view;


import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EmailSystem {

    // Simula l'invio di una mail
    public String inviaMail(String destinatario, String oggetto, String testo) {
        String token = UUID.randomUUID().toString();
        String oggettoConToken = oggetto + " [TOKEN:" + token + "]";
        System.out.println("[EmailSystem] Email inviata a " + destinatario
                + " con oggetto: " + oggettoConToken);
        System.out.println("[EmailSystem] Contenuto: " + testo);
        return token;
    }

    public Boolean leggiRispostaApprova(String token, Date dataMinima) {
        System.out.println("[EmailSystem] Nessun sistema reale di polling attivo.");
        return null;
    }
}

