package com.sineguzov.example.pr1;

import java.util.LinkedList;
import java.util.List;

public class GeneratePrimeNumberExample {

    public static void main(String[] args) {
        System.out.println(primeNumbersGenerator(10));
    }

    public static List<Integer> primeNumbersGenerator(int n) {
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrimeBNumber(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
    public static boolean isPrimeBNumber(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
