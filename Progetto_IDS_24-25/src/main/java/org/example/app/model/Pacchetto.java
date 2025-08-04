package org.example.app.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Pacchetto implements Messaggio{
    private String nome;
    private final UUID id;
    private BigDecimal prezzo;
    private List<Prodotto> prodotti;

    public Pacchetto(String nome, BigDecimal prezzo, List<Prodotto> prodotti) {
        this.nome = nome;
        this.id = UUID.randomUUID();
        this.prezzo = prezzo;
        this.prodotti = prodotti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }
}
