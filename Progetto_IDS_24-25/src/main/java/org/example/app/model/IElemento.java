package org.example.app.model;

import java.math.BigDecimal;
import java.util.UUID;

public interface IElemento {
    public String getNome();
    public UUID getId();
    public BigDecimal calcolaPrezzo();
    public void setQuantità(int quantità);
}
