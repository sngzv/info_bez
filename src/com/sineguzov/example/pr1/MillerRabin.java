package com.sineguzov.example.pr1;

public class MillerRabin {

    static int power(int x, int y, int p) {

        int res = 1;

        x = x % p;

        while (y > 0) {

            if ((y & 1) == 1)
                res = (res * x) % p;

            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }

        return res;
    }

    static boolean miillerTest(int d, int n) {

        // Выбор случайного числа в диапазоне [2..n-2]
        int a = 2 + (int) (Math.random() % (n - 4));

        int x = power(a, d, n);

        if (x == 1 || x == n - 1)
            return true;

        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;

            if (x == 1)
                return false;
            if (x == n - 1)
                return true;
        }

        return false;
    }

    // Возвращает false если n составное и true, если n простое.
    // k - параметр, определяющий уровень точности.
    static boolean isPrime(int n, int k) {

        if (n <= 1 || n == 4)
            return false;
        if (n <= 3)
            return true;

        int d = n - 1;

        while (d % 2 == 0)
            d /= 2;

        for (int i = 0; i < k; i++)
            if (!miillerTest(d, n))
                return false;

        return true;
    }

    public static void main(String args[]) {

        int k = 100; // Количество раундов

        System.out.println(isPrime(877, k));

//        for (int n = 1; n < 100; n++)
//            if (isPrime(n, k))
//                System.out.print(n + " ");
    }

}
