package org.example.app.model;

public class FileInformazioniProdotto implements Messaggio{

    private String nome;
    private Azienda azienda;

    public FileInformazioniProdotto(String nome, Azienda azienda) {
        this.nome = nome;
        this.azienda = azienda;
    }

    public String getNome() {
        return nome;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    @Override
    public String getContenuto() {
        return nome + " " + azienda;
    }
}
