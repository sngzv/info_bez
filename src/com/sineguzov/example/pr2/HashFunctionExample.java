package com.sineguzov.example.pr2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunctionExample {

    // Две строковые переменные для тестрирования работы хеш функции.
    public static final String text1 = "Задача организации, в особенности же дальнейшее развитие";
    public static final String text2 = "Задача организации, в особенности же дальнейшее развитие";

    // В методе сравниваются результаты работы хеш функции для двух текстов. Для одинаковых текстов хеши
    // должны совпасть.
    public static void main(String[] args) {
//        try {
//            String hashText1 = HashUtils.createHash(text1);
//            String hashText2 = HashUtils.createHash(text2);
//            System.out.println("Hash text 1 = " + hashText1);
//            System.out.println("Hash text 2 = " + hashText2);
//            System.out.println(hashText1.equals(hashText2) ? "Тексты идентичны.": "Тексты различаются");
//        } catch (Exception e) {
//            System.out.println("Ошибка при формировании хэш функции.");
//        }
        System.out.println(hash64("hello".getBytes(StandardCharsets.UTF_8), "hello".length(), 3));
    }

    public static class HashUtils {

        // Используется встроенный класс для хеширования SHA-256
        public static String createHash(String text) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        }

        // Преобразование хешированного значения в шестнадцатеричный формат
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

    // Простая хэш функция для строк
    public static String createHashForString(String text) {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        for(final byte b: bytes) {
            sb.append(Integer.toHexString(b & 0xF0 >> 4));
        }

        return sb.toString();
    }

    public static long hash64(final byte[] data, int length, int seed) {
        final long m = 0xc6a4a7935bd1e995L;
        final int r = 47;

        long h = (seed&0xffffffffl)^(length*m);

        int length8 = length/8;

        for (int i=0; i<length8; i++) {
            final int i8 = i*8;
            long k =  ((long)data[i8+0]&0xff)      +(((long)data[i8+1]&0xff)<<8)
                    +(((long)data[i8+2]&0xff)<<16) +(((long)data[i8+3]&0xff)<<24)
                    +(((long)data[i8+4]&0xff)<<32) +(((long)data[i8+5]&0xff)<<40)
                    +(((long)data[i8+6]&0xff)<<48) +(((long)data[i8+7]&0xff)<<56);

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        switch (length%8) {
            case 7: h ^= (long)(data[(length&~7)+6]&0xff) << 48;
            case 6: h ^= (long)(data[(length&~7)+5]&0xff) << 40;
            case 5: h ^= (long)(data[(length&~7)+4]&0xff) << 32;
            case 4: h ^= (long)(data[(length&~7)+3]&0xff) << 24;
            case 3: h ^= (long)(data[(length&~7)+2]&0xff) << 16;
            case 2: h ^= (long)(data[(length&~7)+1]&0xff) << 8;
            case 1: h ^= (long)(data[length&~7]&0xff);
                h *= m;
        };

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        return h;
    }
}
