package com.unicam.ecommerce.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.unicam.ecommerce.JsonPersistence.CarrelloDeserializer;

import java.util.UUID;

public class Account {
    // Attributi principali di un account utente
    private String NomeUtente;        // Nome scelto dall'utente
    private String email;             // Email dell'utente
    private String password;          // Password per l'autenticazione
    private TipoAccount tipo;         // Tipo di account
    private UUID Id;                  // Identificativo univoco generato dal sistema

    @JsonManagedReference
    private Componente componente;    // componente associato all'account
    private Carrello carrello;        // Carrello personale per acquisti online

    /**
     * Costruttore JSON (usato da Jackson per deserializzazione da file/stringa JSON)
     * @param nomeUtente nome utente scelto
     * @param email email dell'utente
     * @param password password utente
     * @param tipologia tipo di account (enum TipoAccount)
     * @param id identificatore univoco (UUID)
     * @param carrello carrello associato all'account
     */
    @JsonCreator
    public Account(
            @JsonProperty("nomeUtente") String nomeUtente,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("tipologia") TipoAccount tipologia,
            @JsonProperty("id") UUID id,
            @JsonProperty("carrello") Carrello carrello
    ) {
        this.NomeUtente = nomeUtente;
        this.password = password;
        this.carrello = carrello;
        this.tipo = tipologia;
        this.Id = id;
    }

    /**
     * Costruttore classico usato dall'applicazione:
     * - genera automaticamente un nuovo UUID
     * - inizializza un nuovo carrello vuoto
     */
    public Account(String nomeUtente, String email, String password, TipoAccount tipologia) {
        this(nomeUtente, email, password, tipologia, UUID.randomUUID(), new Carrello());
    }

    // Getter e Setter

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    @JsonProperty("carrello")
    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getNomeUtente() {
        return NomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        NomeUtente = nomeUtente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoAccount getTipo() {
        return tipo;
    }

    public void setTipo(TipoAccount tipo) {
        this.tipo = tipo;
    }
}
