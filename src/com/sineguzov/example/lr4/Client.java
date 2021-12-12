package com.sineguzov.example.lr4;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class Client {

    static String login = "sineguzov";
    static String password = "Test123";
    static int g = 2;
    long N = 64071183248L;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        String salt =  String.valueOf(random.nextInt());
        String hash = sha256(salt,password);
        String verifier = "88005553535";

        Server server = new Server(login, verifier, salt);

        int a = Math.abs(new Random().nextInt());
        int A = (int) Math.pow(g, a);

        server.authorization(login, A);
    }

    public static String sha256(String s, String p) throws NoSuchAlgorithmException {
        String hashing = s + p;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(hashing.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
