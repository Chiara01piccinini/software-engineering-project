package org.example.app.model;
import java.time.LocalDateTime;
import java.util.Date;

public class FileInformazioniEvento implements Messaggio {
    private Date data;
    private LocalDateTime orario;
    private Position luogo;
    private String nome;
    private int biglietti;

    public FileInformazioniEvento(Date data, LocalDateTime orario, Position luogo, String nome,int biglietti) {
        this.data = data;
        this.orario = orario;
        this.luogo = luogo;
        this.nome = nome;
        this.biglietti=biglietti;
    }

    public int getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(int biglietti) {
        this.biglietti = biglietti;
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

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContenuto() {return data.toString() + orario + luogo + nome; }
}
