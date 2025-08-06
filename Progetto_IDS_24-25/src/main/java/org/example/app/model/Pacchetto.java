package org.example.app.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Pacchetto implements IElemento{
    private String nome;
    private final UUID id;
    private BigDecimal prezzo;
    private final BigDecimal percentualeSconto;
    private Set<Prodotto> prodotti;
    private FileInformazioniTestuale descrizione;

    public Pacchetto(String nome, BigDecimal percentualeSconto, Set<Prodotto> prodotti){
        for (Prodotto p : prodotti){
            if (p.getVendita() == false){
                throw new RuntimeException();
            }
        }

        this.nome = nome;
        this.id = UUID.randomUUID();
        this.percentualeSconto = percentualeSconto;
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

    public BigDecimal calcolaPrezzo() {
        for (Prodotto p : prodotti){
            prezzo = prezzo.add(p.calcolaPrezzo());
        }
        return prezzo.multiply(percentualeSconto).divide(new BigDecimal( 100), RoundingMode.HALF_UP);
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public Set<Prodotto> getProdotti() {
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

    public void setProdotti(Set<Prodotto> prodotti) {
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
