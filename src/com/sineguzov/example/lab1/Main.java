package com.sineguzov.example.lab1;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static final int powerAlphabet = 33; //мощность алфавита

    public static void main(String[] args) {
        String text = "Два эскадрона павлоградцев, перейдя мост, один за другим пошли назад на гору. " +
                "Полковой командир Карл Богданович Шуберт подъехал к эскадрону Денисова и ехал шагом недалеко от Ростова, " +
                "не обращая на него никакого внимания, несмотря на то, что после бывшего столкновения за Телянина " +
                "они виделись теперь в первый раз. Ростов, чувствуя себя во фронте во власти человека, " +
                "перед которым он теперь считал себя виноватым, не спускал глаз с атлетической спины, " +
                "белокурого затылка и красной шеи полкового командира. Ростову то казалось, " +
                "что Богданыч только притворяется невнимательным и что вся цель его теперь состоит в том, " +
                "чтобы испытать храбрость юнкера, и он выпрямлялся и весело оглядывался; то ему казалось, " +
                "что Богданыч нарочно едет близко, чтобы показать Ростову свою храбрость. " +
                "То ему думалось, что враг его теперь нарочно пошлет эскадрон в отчаянную атаку, " +
                "чтобы наказать его, Ростова. То думалось, что после атаки он подойдет к нему и великодушно протянет ему, " +
                "раненому, руку примирения.";
        System.out.println();

        String encryptText = encrypt(text, 3);
        String decryptText = decrypt(encryptText, 3);
        System.out.println(encryptText);
        System.out.println(decryptText);

        int key = methodFrequencyAnalysis(text, encryptText);
        System.out.println(key);

    }

    /**
     * Шифрование текста методом Цезаря.
     *
     * @param text шифруемый текст
     * @param key  ключ
     * @return зашифрованный текст
     */
    public static String encrypt(String text, int key) {
        StringBuilder encryptText = new StringBuilder();

        for (char c : text.toCharArray()) {
            char encryptChar;
            if (c != ' ') {
                encryptChar = (char) (c + key % powerAlphabet);
            } else {
                encryptChar = c;
            }
            encryptText.append(encryptChar);
        }
        return encryptText.toString();
    }

    /**
     * Дешифрование текста зашифрованного методом Цезаря.
     *
     * @param text зашифрованный текст
     * @param key  ключ
     * @return расшифрованный текст
     */
    public static String decrypt(String text, int key) {
        StringBuilder encryptText = new StringBuilder();

        for (char c : text.toCharArray()) {
            char encryptChar;
            if (c != ' ') {
                encryptChar = (char) (c - key % powerAlphabet);
            } else {
                encryptChar = c;
            }
            encryptText.append(encryptChar);
        }
        return encryptText.toString();
    }

    /**
     * Метод частотного анализа.
     *
     * @param sourceText исходный текст
     * @param encryptText зашифрованный текс.
     * @return ключ
     */
    public static int methodFrequencyAnalysis(String sourceText, String encryptText) {
        char sourceMaxChar = sourceText.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> c != ' ')
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get();

        char encryptMaxChar = encryptText.chars()
                .mapToObj(c -> (char) c)
                .filter((c -> c != ' '))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().
                max(Map.Entry.comparingByValue()).
                map(Map.Entry::getKey).get();

        return Math.abs(sourceMaxChar - encryptMaxChar);
    }
}
