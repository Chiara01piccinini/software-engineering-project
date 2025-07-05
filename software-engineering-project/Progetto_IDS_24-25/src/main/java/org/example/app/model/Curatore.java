package org.example.app.model;

import org.example.app.controls.IGestore;
import org.example.app.view.EmailSystem;

public class Curatore extends Componente{
    public Curatore(String nome,String  cognome,int matricola, String email, IGestore gestore) {
        super(nome,cognome,matricola,email, gestore);
    }
public boolean approvaInformazioni(IFileInformazioni info, Venditore venditore, Prodotto prodotto) {
    String subject = "esito approvazione per  " + prodotto.getNome();
    System.out.println("[Curatore] In attesa di risposta email per approvazione...");

    // poling che attende  max 5 minuti con polling ogni 30s
    for (int i = 0; i < 10; i++) {
        try {
            Thread.sleep(30000);//3 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//se la risposta è positiva ritorna true altrimenti false
        if (EmailSystem.leggiRispostaApprova(subject)) {
            System.out.println("[Curatore] Informazione approvata via email.");
            return true;
        }else {
            System.out.println("[Curatore] Nessuna approvazione ricevuta, riprovo...");
        }
    }
    System.out.println("[Curatore] Approvazione non ricevuta entro il tempo limite.");
    return false;
}
    //stampa a video ilmessaggio ricevuto
    @Override
    public void riceviMessaggio(String messaggio) {
        System.out.println("[Curatore]: " + messaggio);
    }
    //todo implementare approvazione per pubblicare pacchdtti e prodotti
    public boolean approvaProdotto(FileInformazioniProdotto prodotto, Venditore venditore){return true;}

    public boolean approvaPacchetto(FileInformazioniPacchetto pacchetto, DistributoreDiTipicita distributore) {return true;}
}
