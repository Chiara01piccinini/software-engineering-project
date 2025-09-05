package org.example.software_engineering_project.model;


import java.math.BigDecimal;
import java.util.UUID;

public interface IElemento {
    String getNome();
    UUID getId();
    BigDecimal calcolaPrezzo();
    void aggiungiInformazioni(Messaggio info);
}

