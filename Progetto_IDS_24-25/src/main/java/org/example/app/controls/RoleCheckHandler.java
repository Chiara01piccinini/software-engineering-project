package org.example.app.controls;

import org.example.app.model.Account;
import org.example.app.model.Marketplace;
import org.example.app.model.tipoAccount;

public class RoleCheckHandler extends AuthHandler {
    private Marketplace sistema;

    public RoleCheckHandler(Marketplace sistema) {
        this.sistema = sistema;
    }

    @Override
    public boolean check(String username, String password) {
        Account account = null;
        for (Account acc : Marketplace.getAccount().values()) {
            if (acc.getNomeUtente().equals(username)) {
                account = acc;
                break;
            }
        }

        if (account == null) {
            System.out.println("[Auth] Utente non trovato");
            return false;
        }

        System.out.println("[Auth] Tipo account: " + account.getTipologia());

        return checkNext(username, password); // passa al prossimo handler
    }
}


