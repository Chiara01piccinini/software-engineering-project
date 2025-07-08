package org.example.app.model;
//rappresenta il comportamento del file testuale, in linea con il pattern startegy
public class FileInformazioniTestuale implements IFileInformazioni {
    private String contenuto;

    public FileInformazioniTestuale(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getContenuto(){
        return contenuto;}

}

