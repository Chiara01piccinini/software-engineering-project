package org.example.app.model;

public class FileInformazioniAccount implements Messaggio {
    private String nomeUtente;
    private String password;
    private String email;

    public FileInformazioniAccount(String nomeUtente, String password, String email,tipoAccount tipo) {
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.email = email;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }


    public String getNome() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getContenuto(){
        return getPassword() + getNome() + getEmail();
    }
}
