package com.sineguzov.example.lab2;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellmanExample {
    private static final byte numBitsSize = 32;
    private static BigInteger p, g;

    public void generateRandomKeys() {
        this.p = new BigInteger(numBitsSize, new Random());
        this.g = new BigInteger(numBitsSize, new Random());
    }

    public BigInteger getAliceMessage(BigInteger a) {
        return this.g.modPow(a, this.p);
    }

    public BigInteger getBobMessage(BigInteger b) {
        return this.g.modPow(b, p);
    }

    public BigInteger aliceCalculationOfKey(BigInteger bobMessage, BigInteger aliceSecretNumber){
        return bobMessage.modPow(aliceSecretNumber, this.p);
    }

    public BigInteger bobCalculationOfKey
            (BigInteger aliceMessage, BigInteger bobSecretNumber){
        return aliceMessage.modPow(bobSecretNumber, this.p);
    }
}
