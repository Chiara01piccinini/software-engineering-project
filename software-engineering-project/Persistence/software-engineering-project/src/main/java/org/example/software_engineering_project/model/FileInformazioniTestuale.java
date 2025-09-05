package org.example.software_engineering_project.model;

//rappresenta il comportamento del file testuale, in linea con il pattern startegy
public class FileInformazioniTestuale implements Messaggio {
    private String contenuto;
    private IElemento prodotto;

    public FileInformazioniTestuale(String contenuto, IElemento prodotto) {
        this.contenuto = contenuto;
        this.prodotto = prodotto;
    }

    public String getContenuto(){
        return contenuto;
    }

    public String getNome() {
        return prodotto.getNome();
    }

    public IElemento getProdotto() {
        return prodotto;
    }
}
