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
        try {
            String hashText1 = HashUtils.createHash(text1);
            String hashText2 = HashUtils.createHash(text2);
            System.out.println("Hash text 1 = " + hashText1);
            System.out.println("Hash text 2 = " + hashText2);
            System.out.println(hashText1.equals(hashText2) ? "Тексты идентичны.": "Тексты различаются");
        } catch (Exception e) {
            System.out.println("Ошибка при формировании хэш функции.");
        }
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
}
