package org.example.software_engineering_project.controls;

public abstract class AuthHandler {
    private AuthHandler next;

    // Imposta il prossimo handler nella catena
    public void setNext(AuthHandler next) {
        this.next = next;
    }

    // Controlla le credenziali e passa al prossimo handler
    public boolean check(String username, String password) {
        if (next != null) {
            return next.check(username, password);
        }
        return true; // Se non ci sono altri handler, ritorna true
    }
}

