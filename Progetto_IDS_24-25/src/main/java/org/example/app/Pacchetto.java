package org.example.app;

import java.math.BigDecimal;
import java.util.List;

public class Pacchetto implements Messaggio{
    private String nome;
    private int id;
    private BigDecimal prezzo;
    private List<Prodotto> prodotti;
}
