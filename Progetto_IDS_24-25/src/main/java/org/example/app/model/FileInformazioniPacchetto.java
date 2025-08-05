package org.example.app.model;

import java.math.BigDecimal;
import java.util.List;

public class FileInformazioniPacchetto implements Messaggio{
    private String nome;
    private BigDecimal prezzo;
    private List<Prodotto> prodotti;

    public FileInformazioniPacchetto(String nome, BigDecimal prezzo, List<Prodotto> prodotti){
        this.nome = nome;
        this.prezzo = prezzo;
        this.prodotti = prodotti;
    }

    public String getNome() {
        return nome;
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
