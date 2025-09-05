package org.example.software_engineering_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class FileInformazioniEvento implements Messaggio {
    private LocalDate data;
    private LocalTime orario;
    private Position luogo;
    private String nome;
    private int biglietti;
    private String descrizione;

    public FileInformazioniEvento(LocalDate data, LocalTime orario, Position luogo, String nome,int biglietti, String descrizione) {
        this.data = data;
        this.orario = orario;
        this.luogo = luogo;
        this.nome = nome;
        this.biglietti=biglietti;
        this.descrizione = descrizione;
    }

    public int getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(int biglietti) {
        this.biglietti = biglietti;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
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

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getDescrizione(){
        return descrizione;
    }

    @Override
    public String getContenuto() {return data.toString() + orario + luogo + nome; }
}
