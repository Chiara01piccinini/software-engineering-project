package org.example.app.model;

public class ProdottoElaborato extends Prodotto{
        private Trasformatore trasformatore;

        public ProdottoElaborato( String nome, Azienda azienda, Trasformatore trasformatore) {
                super(nome, azienda);
                this.trasformatore = trasformatore;
        }

        public Trasformatore getTrasformatore() {
                return trasformatore;
        }
}
