package org.example.app.model;

import java.util.HashMap;

public class Account {
    private String nomeUtente;
    private String password;
    private HashMap<IElemento, Integer> carrello;

    public Account(String nomeUtente, String password, HashMap<IElemento, Integer> carrello) {
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.carrello = carrello;
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
        if (elemento != null && quantita > 0 &&){

        }
        carrello.put(elemento, quantita);
    }
}
