package org.example.app.model;

//rappresenta tutti i prodotti che possono essre caricati
public class Prodotto {
    private int id;
    private String nome;
    private Boolean vendita=false;
    private FileInformazioniTestuale descrizione;
    private FileInformazioniImmagini foto;

    public Prodotto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getVendita() {
        return vendita;
    }

    public void setVendita(Boolean vendita) {
        this.vendita = vendita;
    }

    public FileInformazioniTestuale getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(FileInformazioniTestuale descrizione) {
        this.descrizione = descrizione;
    }

    public FileInformazioniImmagini getFoto() {
        return foto;
    }

    public void setFoto(FileInformazioniImmagini foto) {
        this.foto = foto;
    }
//modifica le informazioni del prodotto
    public void aggiungiInformazioni(IFileInformazioni fileInfo) {
        if (fileInfo instanceof FileInformazioniTestuale) {
           setDescrizione((FileInformazioniTestuale) fileInfo);
        } else if (fileInfo instanceof FileInformazioniImmagini immagine) {
           setFoto((FileInformazioniImmagini) fileInfo);}
    }

}
