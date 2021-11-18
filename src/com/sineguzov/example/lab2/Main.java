package com.sineguzov.example.lab2;

import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static final byte numBitsSize = 32;
    public static final BigInteger a = new BigInteger(numBitsSize, new Random());
    public static final BigInteger b = new BigInteger(numBitsSize, new Random());

    public static void main(String[] args) {
        DiffieHellmanExample diffieHellman = new DiffieHellmanExample();
        diffieHellman.generateRandomKeys();
        BigInteger A = diffieHellman.getAliceMessage(a);
        BigInteger B = diffieHellman.getBobMessage(b);
        BigInteger messageAlice = diffieHellman.aliceCalculationOfKey(B, a);
        BigInteger messageBob = diffieHellman.bobCalculationOfKey(A, b);
        System.out.println(String.format("Message Alice %s and message Bob %s", messageAlice, messageBob));
    }}