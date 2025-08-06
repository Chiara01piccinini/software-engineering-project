package org.example.app.model;

import java.math.BigDecimal;
import java.util.Set;

public class FileInformazioniPacchetto implements Messaggio{
    private String nome;
    private BigDecimal percentualeSconto;
    private Set<Prodotto> prodotti;
    private int quantita;

    public FileInformazioniPacchetto(String nome, BigDecimal percentualeSconto, Set<Prodotto> prodotti, int quantita){
        this.nome = nome;
        this.percentualeSconto = percentualeSconto;
        this.prodotti = prodotti;
        this.quantita = quantita;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPercentualeSconto() {
        return percentualeSconto;
    }

    public Set<Prodotto> getProdotti() {
        return prodotti;
    }

    public int getQuantita(){
        return quantita;
    }

    @Override
    public String getContenuto() {
        return prodotti + " " + percentualeSconto;
    }
}
