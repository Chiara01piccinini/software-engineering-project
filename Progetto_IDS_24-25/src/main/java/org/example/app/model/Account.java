package org.example.app.model;

import java.util.HashMap;
import java.util.UUID;

public class Account {
    private String nomeUtente;
    private String password;
    private HashMap<IElemento, Integer> carrello;
    private tipoAccount tipologia;
    private UUID id;

    public Account(String nomeUtente, String password, tipoAccount tipologia) {
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.carrello = new HashMap<>();
        this.tipologia = tipologia;
        this.id = UUID.randomUUID(); // genera un ID unico
    }

    public void setCarrello(HashMap<IElemento, Integer> carrello) {
        this.carrello = carrello;
    }

    public tipoAccount getTipologia() {
        return tipologia;
    }

    public void setTipologia(tipoAccount tipologia) {
        this.tipologia = tipologia;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<IElemento, Integer> getCarrello() {
        return carrello;
    }

    public void aggiungiElemento(IElemento elemento, Integer quantita) {
        if (elemento != null && quantita > 0 ){
        carrello.put(elemento, quantita);
        }
    }
}
