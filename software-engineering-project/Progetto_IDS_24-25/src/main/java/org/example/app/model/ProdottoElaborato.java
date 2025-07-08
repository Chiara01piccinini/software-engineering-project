package org.example.app.model;

public class ProdottoElaborato extends Prodotto{
        private Trasformatore trasformatore;

        public ProdottoElaborato(int id, String nome, Azienda azienda, Trasformatore trasformatore) {
                super(id, nome, azienda);
                this.trasformatore = trasformatore;
        }
}
