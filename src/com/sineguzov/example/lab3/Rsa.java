package com.sineguzov.example.lab3;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Rsa {

    public static void main(String args[]) {
        long p, q, n, z, d = 0, e, i;

        int msg = 111111;
        long c;

        System.out.println("Шифруемое сообщение  : " + msg);

        p = 3557; // 1 простое случайное число
        q = 2579;// 2 простое случайно число

        n = p * q; //n - модуль
        z = (p - 1) * (q - 1); // вычисление функции Эйлера от n

        for (e = 2; e < z; e++) { // вычисляется число (1 < e < f(n)) взаимно простое с результатом вычисления функции Эйлера
            if (check(e, z) == 1) {
                break;
            }
        }

        System.out.println(String.format("Пара (%d, %d) является открытым ключом", e, n));

        for (i = 0; i <= 9; i++) { // вычисляется секретная экспонента (мультипликативно обратное к числу e)
            long x = 1 + (i * z);
            if (x % e == 0) {
                d = x / e;
                break;
            }
        }

        System.out.println(String.format("Пара (%d, %d) является закрытым ключом", d, n));

        c = (long) (Math.pow(msg, e)) % n; // вычисление шифротекста
        System.out.println("Зашифрованное сообщение : " + c);

        BigDecimal msgback = BigDecimal.valueOf(c).pow((int) d).remainder(BigDecimal.valueOf(n));
        System.out.println("Расшифрованное сообщение : " + msgback);
    }

    static long check(long e, long z) {
        if (e == 0)
            return z;
        else
            return check(z % e, e);
    }
}
