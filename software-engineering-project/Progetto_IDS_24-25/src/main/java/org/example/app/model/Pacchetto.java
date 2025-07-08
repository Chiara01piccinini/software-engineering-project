package org.example.app.model;

import java.math.BigDecimal;
import java.util.List;

public class Pacchetto {
    private String nome;
    private int id;
    private BigDecimal prezzo;
    private List<Prodotto> prodotti;

    public Pacchetto(String nome, int id, BigDecimal prezzo, List<Prodotto> prodotti) {
        this.nome = nome;
        this.id = id;
        this.prezzo = prezzo;
        this.prodotti = prodotti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
