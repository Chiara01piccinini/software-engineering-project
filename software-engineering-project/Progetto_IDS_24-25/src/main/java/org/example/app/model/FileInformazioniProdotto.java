package org.example.app.model;

public class FileInformazioniProdotto implements IFileInformazioni{
    private int id;
    private String nome;
    private Azienda azienda;

    public FileInformazioniProdotto(int id, String nome, Azienda azienda) {
        this.id = id;
        this.nome = nome;
        this.azienda = azienda;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    @Override
    public String getContenuto() {
        return id + " " + nome + " " + azienda;
    }
}
