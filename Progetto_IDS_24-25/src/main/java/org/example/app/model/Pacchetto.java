package org.example.app.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Pacchetto {
    private String nome;
    private final UUID id;
    private BigDecimal prezzo;
    private List<Prodotto> prodotti;
    private FileInformazioniTestuale descrizione;

    public Pacchetto(String nome, BigDecimal prezzo, List<Prodotto> prodotti) {
        this.nome = nome;
        this.id = UUID.randomUUID();
        this.prezzo = prezzo;
        this.prodotti = prodotti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public FileInformazioniTestuale getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(FileInformazioniTestuale descrizione) {
        this.descrizione = descrizione;
    }
    public String getContenuto() {
        String prodottiStr = prodotti.stream()
                .map(Prodotto::getNome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        String desc = descrizione != null ? descrizione.getContenuto() : "";
        return "Pacchetto: " + nome + ", Prodotti: [" + prodottiStr + "], Prezzo: " + prezzo + ", Descrizione: " + desc;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }
    public void aggiungiInformazioni(Messaggio info) {
        if (info instanceof FileInformazioniTestuale) {
            setDescrizione((FileInformazioniTestuale) info);
        } else {
            System.out.println("[Pacchetto] Tipo di informazione non supportato: " + info.getClass().getSimpleName());
        }
    }
}
