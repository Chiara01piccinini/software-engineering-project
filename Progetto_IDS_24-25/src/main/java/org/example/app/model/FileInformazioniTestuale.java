package org.example.app.model;
//rappresenta il comportamento del file testuale, in linea con il pattern startegy
public class FileInformazioniTestuale implements Messaggio {
    private String contenuto;
    private IElemento elemento;

    public FileInformazioniTestuale(String contenuto, IElemento elemento) {
        this.contenuto = contenuto;
        this.elemento = elemento;
    }

    public String getContenuto(){
        return contenuto;
    }

    public String getNome() {
        return elemento.getNome();
    }

    public IElemento getElemento() {
        return elemento;
    }
}

