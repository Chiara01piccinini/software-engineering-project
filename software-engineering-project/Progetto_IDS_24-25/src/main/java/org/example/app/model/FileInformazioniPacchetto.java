package org.example.app.model;

import java.math.BigDecimal;
import java.util.List;

public class FileInformazioniPacchetto implements IFileInformazioni{
    private String nome;
    private int id;
    private BigDecimal prezzo;
    private List<Prodotto> prodotti;

    public FileInformazioniPacchetto(String nome, int id, BigDecimal prezzo, List<Prodotto> prodotti){
        this.nome = nome;
        this.id = id;
        this.prezzo = prezzo;
        this.prodotti = prodotti;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    @Override
    public String getContenuto() {
        return prodotti + " " + prezzo;
    }
}
