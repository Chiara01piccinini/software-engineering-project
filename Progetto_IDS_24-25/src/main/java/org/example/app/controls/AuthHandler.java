package org.example.app.controls;

public abstract class AuthHandler {
    private AuthHandler next;

    public AuthHandler linkWith(AuthHandler next) {
        this.next = next;
        return next;
    }

    public boolean checkNext(String username, String password) {
        if (next == null) {
            return true;
        }
        return next.check(username, password);
    }

    public abstract boolean check(String username, String password);
}

