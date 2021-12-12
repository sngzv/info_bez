package com.sineguzov.example.lab1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CaesarCipher {

    public static final String keyword = "шифровка";
    public static final int key = 3;
    public static final String alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    public static String message = "Два эскадрона павлоградцев, перейдя мост, один за другим пошли назад на гору. " +
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
   static String textForFrequencyAnalysis = "Пьер хорошо знал эту большую, разделенную колоннами и аркой комнату, всю обитую персидскими коврами. " +
            "Часть комнаты за колоннами, где с одной стороны стояла высокая красного дерева кровать под шелковыми занавесами, а с другой — огромный киот с образами, " +
            "была красно и ярко освещена, как бывают освещены церкви во время вечерней службы. Под освещенными ризами киота стояло длинное вольтеровское кресло, " +
            "и на кресле, обложенном вверху снежно-белыми, не смятыми, видимо, только что перемененными подушками, укрытая до пояса ярко-зеленым одеялом, " +
            "лежала знакомая Пьеру величественная фигура его отца, графа Безухова, с тою же седою гривой волос, " +
            "напоминавших льва, над широким лбом и с теми же характерноблагородными крупными морщинами на красивом красно-желтом лице. " +
            "Он лежал прямо под образами; обе толстые, большие руки его были выпростаны из-под одеяла и лежали на нем. В правую руку, лежавшую ладонью книзу, " +
            "между большим и указательным пальцами вставлена была восковая свеча, которую, нагибаясь из-за кресла, придерживал в ней старый слуга. " +
            "Над креслом стояли духовные лица в своих величественных блестящих одеждах, с выпростанными на них длинными волосами, " +
            "с зажженными свечами в руках, и медленно-торжественно служили. Немного позади их стояли две младшие княжны, с платком в руках и у глаз, и впереди их старшая, " +
            "Катишь, с злобным и решительным видом, ни на мгновение не спуская глаз с икон, как будто говорила всем, что не отвечает за себя, если оглянется. " +
            "Анна Михайловна, с кроткою печалью и всепрощением на лице, и неизвестная дама стояли у двери. Князь Василий стоял с другой стороны двери, " +
            "близко к креслу, за резным бархатным стулом, который он поворотил к себе спинкой, и, облокотив на нее левую руку со свечой, " +
            "крестился правою, каждый раз поднимая глаза кверху, когда приставлял персты ко лбу. Лицо его выражало спокойную набожность и преданность воле Божией. " +
            "«Ежели вы не понимаете этих чувств, то тем хуже для вас», — казалось, говорило его лицо.";
    public static final int powerAlphabet = 33;

    //  абвгдеёжзийклмнопрстуфхцчшщъьэюя
    //  эюясинегузовабдёжйклмнпртфхчшщъь

    public static String encrypt(String msg) {
        String modifiedString = "";
        for (char c : alphabet.toCharArray()) {
            if (!keyword.contains(String.valueOf(c))) {
                modifiedString += c;
            }
        }

        int length = modifiedString.length();

        String ciphertext = modifiedString.substring(length - key, length)
                + keyword
                + modifiedString.substring(0, length - 3);

        char cc = 1024;

        msg = msg.toLowerCase().replaceAll("[^a-z-а-я]", "")
                .replaceAll("-", "");

        String a = "";
        for (char c : msg.toCharArray()) {
            a += ciphertext.charAt(alphabet.indexOf(c));
        }

        return a;
    }

    public static void main(String[] args) {
        String encryptText = encrypt(textForFrequencyAnalysis);
        String sourceText = textForFrequencyAnalysis.toLowerCase().replaceAll("[^a-z-а-я]", "")
                .replaceAll("-", "");
        String decryptText = matchMonograms(sourceText, encryptText);

        String encBi = matchBigrams(sourceText, encryptText);
        String upBi = updateMethodBigram(sourceText, encryptText);

        System.out.println(encryptText);
        System.out.println(decryptText);
    }

    public static Map countNumberOfMonograms(String text) {
        Map<String, Long> monograms = Arrays.asList(text.split("(?<=\\G.{1})")).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return monograms;
    }

    public static String matchMonograms(String text, String decryptText) {
        Map<String, Long> textMonograms = countNumberOfMonograms(text);
        Map<String, Long> sortTextMonograms = new LinkedHashMap<>();
        Map<String, Long> decryptTextMonograms = countNumberOfMonograms(decryptText);
        Map<String, Long> sortDecryptTextMonograms = new LinkedHashMap<>();

        textMonograms.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortTextMonograms.put(x.getKey(), x.getValue()));

        decryptTextMonograms.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortDecryptTextMonograms.put(x.getKey(), x.getValue()));

        List<String> l1 = new ArrayList<>(sortTextMonograms.keySet());
        List<String> l2 = new ArrayList<>(sortDecryptTextMonograms.keySet());

        System.out.println("Самые популярные монограммы: \n" + sortTextMonograms + "\n" + sortDecryptTextMonograms);

        String changeMessage = decryptText;
        StringBuilder sb = new StringBuilder(changeMessage);

//        for (int i = 0; i < l2.size(); i++) {
//            changeMessage = changeMessage.replaceAll(l2.get(i), l1.get(i));
//        }

        for (int i = 0; i < changeMessage.length() -1; i++) {
            char c = changeMessage.charAt(i);
            int idx = l2.indexOf(String.valueOf(c));
            if (idx >= 0) {
                String changeChar = l1.get(idx);
                sb.setCharAt(i, changeChar.charAt(0));
            }
        }

        return sb.toString();
    }

    public static String matchBigrams(String text, String decryptText) {
        Map<String, Long> textBigrams = countNumberOfBigrams(text);
        Map<String, Long> sortTextBigrams = new LinkedHashMap<>();
        Map<String, Long> decryptTextBigrams = countNumberOfBigrams(decryptText);
        Map<String, Long> sortDecryptTextBigrams = new LinkedHashMap<>();

        List<String> popularMonograms = Arrays.asList("о", "а", "е", "и", "н", "л", "с", "р", "в", "т", "к");

        textBigrams.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortTextBigrams.put(x.getKey(), x.getValue()));

        decryptTextBigrams.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortDecryptTextBigrams.put(x.getKey(), x.getValue()));

        System.out.println("Самые популярные биграммы: \n" + sortTextBigrams + "\n" + sortDecryptTextBigrams);

        List<String> l1 = new ArrayList<>(sortTextBigrams.keySet());
        List<String> l2 = new ArrayList<>(sortDecryptTextBigrams.keySet());

        l1 = l1.subList(0, 10);
        l2 = l2.subList(0, 10);

        String changeMessage = decryptText;
        StringBuilder sb = new StringBuilder(changeMessage);

        for (int i = 0; i < l2.size(); i++) {
            changeMessage = changeMessage.replaceAll(l2.get(i), l1.get(i));
        }
//        for (int i = 0; i < changeMessage.length() -1; i++) {
//            char c = changeMessage.charAt(i);
//            int idx = l2.indexOf(String.valueOf(c));
//            if (idx >= 0) {
//                String changeChar = l1.get(idx);
//                sb.setCharAt(i, changeChar.charAt(0));
//            }
//        }

        return sb.toString();
    }

    public static String updateMethodBigram(String text, String decryptText) {
        Map<String, Long> textMonograms = countNumberOfMonograms(text);
        Map<String, Long> sortTextMonograms = new LinkedHashMap<>();
        Map<String, Long> decryptTextMonograms = countNumberOfMonograms(decryptText);
        Map<String, Long> sortDecryptTextMonograms = new LinkedHashMap<>();

        textMonograms.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortTextMonograms.put(x.getKey(), x.getValue()));

        decryptTextMonograms.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortDecryptTextMonograms.put(x.getKey(), x.getValue()));

        List<String> l1 = new ArrayList<>(sortTextMonograms.keySet());
        List<String> l2 = new ArrayList<>(sortDecryptTextMonograms.keySet());

        Map<String, Long> textBigrams = countNumberOfBigrams(text);
        Map<String, Long> sortTextBigrams = new LinkedHashMap<>();
        Map<String, Long> decryptTextBigrams = countNumberOfBigrams(decryptText);
        Map<String, Long> sortDecryptTextBigrams = new LinkedHashMap<>();

        List<String> popularMonograms = Arrays.asList("о", "а", "е", "и", "н", "л", "с", "р", "в", "т", "к");

        textBigrams.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortTextBigrams.put(x.getKey(), x.getValue()));

        decryptTextBigrams.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortDecryptTextBigrams.put(x.getKey(), x.getValue()));

        System.out.println("Самые популярные биграммы: \n" + sortTextBigrams + "\n" + sortDecryptTextBigrams);

        List<String> l3 = new ArrayList<>(sortTextBigrams.keySet());
        List<String> l4 = new ArrayList<>(sortDecryptTextBigrams.keySet());

        for (int i = 0; i < l4.size(); i++) {
            String soBi = l4.get(i);
            String decBi = l3.get(i);
            if (soBi.length() > 1 && decBi.length() > 1) {
                char soBi1 = soBi.charAt(0);
                char soBi2 = soBi.charAt(1);

                char decBi1 = decBi.charAt(0);
                char decBi2 = decBi.charAt(1);

                int idx1 = l1.indexOf(String.valueOf(soBi1));
                int idx2 = l1.indexOf(String.valueOf(soBi2));
                if (idx1 >= 0 && idx2 >= 0) {
                    String sIdx1 = l1.get(idx1);
                    String sIdx2 = l1.get(idx2);
                    l1.set(idx1, String.valueOf(soBi1));
                    l1.set(idx2, String.valueOf(soBi2));
//                    l1.add(idx1, String.valueOf(soBi1));
//                    l1.add(idx2, String.valueOf(soBi2));
                    l2.set(idx1, String.valueOf(decBi1));
                    l2.set(idx2, String.valueOf(decBi2));
//                    l2.add(idx1, String.valueOf(decBi1));
//                    l2.add(idx2, String.valueOf(decBi2));

                    int idxxxx1 = l1.indexOf(sIdx1);
                    int idxxxx2 = l2.indexOf(sIdx2);
                    if (idxxxx1 >= 0 && idxxxx2 >= 0) {
                        l1.set(idxxxx1, sIdx1);
                        l2.set(idxxxx2, sIdx2);
                    }
                }
            }
        }

//        System.out.println("Таблица монограм после преобразования биграммами: \n" + l1 + "\n" + l2);

        String changeMessage = decryptText;
        StringBuilder sb = new StringBuilder(changeMessage);

        for (int i = 0; i < changeMessage.length() -1; i++) {
            char c = changeMessage.charAt(i);
            int idx = l2.indexOf(String.valueOf(c));
            if (idx >= 0) {
                String changeChar = l1.get(idx);
                sb.setCharAt(i, changeChar.charAt(0));
            }
        }

        return sb.toString();
    }

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
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get();

        return Math.abs(sourceMaxChar - encryptMaxChar);
    }

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

    public static Map countNumberOfBigrams(String text) {

        String formattingText = text.toLowerCase().replaceAll("[^a-z-а-я]", "");
        List<String> pairString = Arrays.asList(formattingText.split("(?<=\\G.{2})"));
        Map<String, Long> map = pairString.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return map;
    }
}
