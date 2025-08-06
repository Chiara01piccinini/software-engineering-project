package org.example.app.model;

public class ProdottoBase extends Prodotto{
    private final Produttore produttore;

    public ProdottoBase( String nome, Azienda azienda, Produttore produttore) {
        super(nome, azienda);
        this.produttore = produttore;
    }

    public Produttore getProduttore() {
        return produttore;
    }
}
