package com.sineguzov.example.lr4;

import java.util.Random;

public class Server {

    String login;
    String verifier;
    String salt;
    String publicKey;

    public void authorization(String login, int publicKey) {
        if (publicKey != 0) {
            int b = Math.abs(new Random().nextInt());
        }

    }

    public Server(String login, String verifier, String salt) {
        this.login = login;
        this.verifier = verifier;
        this.salt = salt;
    }

    public String getLogin() {
        return login;
    }

    public String getVerifier() {
        return verifier;
    }

    public String getSalt() {
        return salt;
    }
}
