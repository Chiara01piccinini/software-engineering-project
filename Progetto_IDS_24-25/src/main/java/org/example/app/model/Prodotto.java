package org.example.app.model;

import java.util.Optional;
import java.util.UUID;

//rappresenta tutti i prodotti che possono essre caricati
public class Prodotto implements Messaggio {
    private final UUID id;
    private String nome;
    private Azienda azienda;
    private Boolean vendita=false;
    private FileInformazioniTestuale descrizione;
    private FileInformazioniImmagini foto;

    public Prodotto(String nome, Azienda azienda) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.azienda = azienda;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Azienda getAzienda(){return azienda;}

    public void setAzienda (Azienda azienda) {this.azienda = azienda;}

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
