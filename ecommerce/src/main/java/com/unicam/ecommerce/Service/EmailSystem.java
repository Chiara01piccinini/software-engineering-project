package com.unicam.ecommerce.Service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * EmailSystem simula un sistema di invio e gestione di email.
 *
 * Compiti principali:
 * 1. inviaMail(String destinatario, String oggetto, String testo):
 *    - Simula l'invio di un'email generando un token univoco.
 *    - Stampa a console i dettagli dell'email inviata.
 *    - Restituisce il token generato, utile per tracciare la richiesta.
 *
 * 2. leggiRispostaApprova(String token, Date dataMinima):
 *    - Metodo placeholder per simulare la lettura di una risposta all'email.
 *    - Attualmente non implementato con un sistema reale di polling.
 *
 * Nota:
 * Questa classe è utile principalmente per simulazioni o testing.
 * In un ambiente reale, andrebbe sostituita con un servizio SMTP o API di email.
 */
@Service
public class EmailSystem {

    // Simula l'invio di una mail e genera un token univoco
    public String inviaMail(String destinatario, String oggetto, String testo) {
        String token = UUID.randomUUID().toString();
        String oggettoConToken = oggetto + " [TOKEN:" + token + "]";
        System.out.println("[EmailSystem] Email inviata a " + destinatario
                + " con oggetto: " + oggettoConToken);
        System.out.println("[EmailSystem] Contenuto: " + testo);
        return token;
    }

    // Placeholder per leggere eventuali risposte alle email inviate
    public Boolean leggiRispostaApprova(String token, Date dataMinima) {
        System.out.println("[EmailSystem] Nessun sistema reale di polling attivo.");
        return null;
    }
}
