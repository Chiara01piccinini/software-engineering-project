package org.example.software_engineering_project.controls;

import org.example.software_engineering_project.model.Account;
import org.springframework.stereotype.Component;

@Component
public class Session {
    private Account currentUser;
    private boolean authenticated = false;

    public Account getCurrentUser() { return currentUser; }

    public void setCurrentUser(Account currentUser) {
        this.currentUser = currentUser;
        this.authenticated = currentUser != null;
    }

    public boolean isAuthenticated() { return authenticated; }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void logout() {
        this.currentUser = null;
        this.authenticated = false;
    }
}
