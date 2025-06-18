package org.example.app.model;

import org.example.app.controls.GestorePubblicazioni;
import org.example.app.controls.IGestore;
//rappresenta tutti i venditori,ovvero coloro che possono vendere dei prrodotti nel marketplace
public abstract class Venditore  extends Componente{
    private int matricola;
    private String email;
    private GestorePubblicazioni gestore;
    private String nome;
    private String cognome;

    public Venditore( String nome, String cognome, int matricola, String email) {
        super(nome,cognome,matricola,email);
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGestore(GestorePubblicazioni gestore) {
        this.gestore = gestore;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getMatricola() {
        return matricola;
    }

    public String getEmail() {
        return email;
    }

    public GestorePubblicazioni getGestore() {
        return gestore;
    }
    //invoca il gestore per inviare i file
    public void inviaFile(GestorePubblicazioni gestore,IFileInformazioni info ){
     gestore.send(this,info);
    };
    //stampa a video ilmessaggio ricevuto
    public void riceviMessaggio(String messaggio) {
        System.out.println("[Messaggio]: " + messaggio);
    }
}
