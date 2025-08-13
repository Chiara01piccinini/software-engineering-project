package org.example.app.controls;

import org.example.app.model.Account;
import org.example.app.model.Marketplace;
import org.example.app.model.tipoAccount;

public class RoleCheckHandler extends AuthHandler{
    private Marketplace sistema;

    public RoleCheckHandler(Marketplace sistema) {
        this.sistema = sistema;
    }

    @Override
    public boolean check(String username, String password) {
        Account account = sistema.getAccount().get(username);

        if (account == null) {
            System.out.println("[Auth] Utente non trovato");
            return false;
        }
        tipoAccount role = account.getTipologia();
        System.out.println("[Auth] Tipo account: " + role);

        return checkNext(username, password);
    }
}

