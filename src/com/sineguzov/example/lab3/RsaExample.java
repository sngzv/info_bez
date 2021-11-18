package com.sineguzov.example.lab3;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class RsaExample {

    private final static int exhibitor = 3;
    private final static int p = 3557;
    private final static int q = 2579;
    private final static long message = 111111;

    static int gcd(int a, int b)
    {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    static int phi(int n)
    {
        int result = 1;
        for (int i = 2; i < n; i++)
            if (gcd(i, n) == 1)
                result++;
        return result;
    }

    static int secretExhibitor(int ef) {
        return (2 * ef + 1) / exhibitor;
    }

    public static void main(String[] args) {
        int n = p * q; //вычисляется произведение двух простых чисел
        int eulerResult = phi(n); //вычисляется функция Эйлера
        int d = secretExhibitor(eulerResult); //вычисляется секретная экспонента
        long c = (long) Math.pow(message, exhibitor) % n; //вычисляется шифротекст
        BigDecimal enc = BigDecimal.valueOf(c).pow(d).remainder(BigDecimal.valueOf(n)); //вычисляется исходное сообщение
        System.out.println(String.format("Исходное сообщение %s, сообщение после расшифровки %s",
                message,enc));
    }
}
