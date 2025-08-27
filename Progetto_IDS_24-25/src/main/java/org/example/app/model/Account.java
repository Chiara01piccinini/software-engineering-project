package org.example.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.UUID;

public class Account {
    private String nomeUtente;
    private String password;
    private HashMap<UUID, Integer> carrello;
    private tipoAccount tipologia;
    private UUID id;

    @JsonCreator
    public Account(@JsonProperty("nomeUtente") String nomeUtente,@JsonProperty("password") String password,@JsonProperty("tipologia") tipoAccount tipologia,@JsonProperty("id") UUID id,@JsonProperty("carrello") HashMap<UUID, Integer> carrello ) {
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.carrello = carrello;
        this.tipologia = tipologia;
        this.id = id;
    }

    public Account(String nomeUtente, String password, tipoAccount tipologia){
        this(nomeUtente,password,tipologia,UUID.randomUUID(),new HashMap<>());
    }

    public void setCarrello(HashMap<UUID, Integer> carrello) {
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

    public HashMap<UUID, Integer> getCarrello() {
        return carrello;
    }

    public void aggiungiElemento(IElemento elemento, Integer quantita) {
        if (elemento != null && quantita > 0 ){
        carrello.put(elemento.getId(), quantita);
        }
    }
}
