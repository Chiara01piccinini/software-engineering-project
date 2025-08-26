package org.example.app.model;

import org.example.app.controls.GestoreAcquisti;
import org.example.app.controls.Session;

import java.util.ArrayList;

//rappresenta le classi che si scambiano messaggi all'interno del pattern mediator
public class Componente {
    private Account account;
    private int matricola;
    private String email;
    private String nome;
    private String cognome;
    private ArrayList<String> prenotazioni;
    private Marketplace sistema;

    public Componente(Account account, int matricola,String email) {
        this.account = account;
        this.matricola = matricola;
        this.email=email;
        this.prenotazioni= new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmail() {
        return  email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public void getPrenotazioni() {
        for(int i=0; i < this.prenotazioni.size();i++){
            System.out.println(prenotazioni.get(i));
        }
        return;
    }
    public void prenotaEvento(Evento evento,int np){
        if (!Session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if(evento.getBiglietti()>= np){
            evento.setBiglietti(evento.getBiglietti()-np);
            System.out.println("prenotazione di"+np+"biglietti per l'evento"+evento.getNome()+"avvenuto");
            prenotazioni.add(
                    "prenotazione per"+evento.getNome()+"numero biglietti : " + np+"data :"+evento.getData()+"orario :" +
                            evento.getOrario()+ "luogo :" + evento.getLuogo().getNome());
        }

    }
    public void visualizzaContenuti(){
        System.out.println(this.sistema.getProdotti());
        System.out.println(this.sistema.getEventi());
        System.out.println(this.sistema.getPacchetti());
    }
    public void visualizzaProdotto(Prodotto prodotto){
        if(sistema.getProdotti().containsValue(prodotto)){
            System.out.println("visualizzazione prodotto:"+prodotto.getNome()+ prodotto.getFoto()+
                    "azienda produttrice:" + prodotto.getAzienda()+
                    "quantità disponibile : " + prodotto.getQuantità() +
                    "prezzo: " + prodotto.getPrezzo()+
                    "descrizione:"+  prodotto.getDescrizione());
        }
    }
    public void visualizzaPacchetto(Pacchetto pacchetto){
        if(sistema.getPacchetti().containsValue(pacchetto)){
            System.out.println("visualizzazione pacchetto:"+pacchetto.getNome()+
                    "quantità disponibile : " + pacchetto.getQuantità() +
                    "descrizione:"+  pacchetto.getDescrizione()+
                    "prodotti compresi:"+ pacchetto.getProdotti()+
                    "prezzo:" + pacchetto.getPrezzo());
        }
    }
    public void visualizzaEvento(Evento evento) {
        if (sistema.getEventi().containsValue(evento)) {
            System.out.println("visualizzazione evento:" + evento.getNome() +
                            "posti disponibili : " + evento.getBiglietti() +
                            "descrizione:" + evento.getDescrizione() +
                            "data:" + evento.getData()+
                            "orario:" + evento.getOrario() +
                            "luogo:" + evento.getLuogo());
        }
    }

    public void acquista(GestoreAcquisti gestore, IElemento elemento, int quantità) {
        if (!Session.isAuthenticated()) {
            throw new SecurityException("Operazione non consentita: utente non autenticato");
        }
        if (elemento instanceof Prodotto) {
            gestore.acquistaProdotto(this, (Prodotto) elemento, quantità);
        } else if (elemento instanceof Pacchetto) {
            gestore.acquistaPacchetto(this, (Pacchetto) elemento, quantità);
        }
    }


    public  void riceviMessaggio(String messaggio){
            System.out.println("[Messaggio]: " + messaggio);
    };
}
