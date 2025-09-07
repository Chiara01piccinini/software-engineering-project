package org.example.software_engineering_project.controls;

import org.example.software_engineering_project.model.Account;
import org.example.software_engineering_project.model.Marketplace;
import org.springframework.stereotype.Service;

@Service
public class RoleCheckHandler extends AuthHandler {
    private final Marketplace sistema;

    public RoleCheckHandler(Marketplace sistema) {
        this.sistema = sistema;
    }

    @Override
    public boolean check(String username, String password) {
        Account account = sistema.getAccountByUsername(username);

        if (account == null) {
            System.out.println("[Auth] Utente non trovato");
            return false;
        }

        System.out.println("[Auth] Tipo account: " + account.getTipologia());

        return super.check(username, password); // passa al prossimo handler
    }
}
