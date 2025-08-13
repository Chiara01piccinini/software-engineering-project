package org.example.app.model;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Evento  {
    private Date data;
    private LocalDateTime orario;
    private Position luogo;
    private FileInformazioniTestuale descrizione;
    private UUID ID;
    private String nome;
    private int biglietti;

    public Evento(Date data, LocalDateTime orario, Position luogo,String nome,int biglietti) {
        this.data = data;
        this.orario = orario;
        this.luogo = luogo;
        this.nome=nome;
        this.biglietti=biglietti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public LocalDateTime getOrario() {
        return orario;
    }

    public void setOrario(LocalDateTime orario) {
        this.orario = orario;
    }

    public Position getLuogo() {
        return luogo;
    }

    public void setLuogo(Position luogo) {
        this.luogo = luogo;
    }

    public FileInformazioniTestuale getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(FileInformazioniTestuale descrizione) {
        this.descrizione = descrizione;
    }

    public int getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(int biglietti) {
        this.biglietti = biglietti;
    }

    public void rimuoviBiglietto(int prenotazione){
       setBiglietti(this.biglietti - prenotazione);
       return;
    }
}

