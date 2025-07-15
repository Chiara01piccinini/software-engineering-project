package org.example.app.model;
//rappresenta il comportamento del file testuale, in linea con il pattern startegy
public class FileInformazioniTestuale implements IFileInformazioni {
    private String contenuto;
    private Prodotto prodotto;

    public FileInformazioniTestuale(String contenuto, Prodotto prodotto) {
        this.contenuto = contenuto;
        this.prodotto = prodotto;
    }

    public String getContenuto(){
        return contenuto;
    }

    public String getNome() {
        return prodotto.getNome();
    }

    public Prodotto getProdotto() {
        return prodotto;
    }
}

