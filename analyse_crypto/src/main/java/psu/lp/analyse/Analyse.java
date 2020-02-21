package psu.lp.analyse;

import java.io.*;
import java.util.*;

public class Analyse {

    private static Map<Character, Double> x = new HashMap<>();
    private final static char[] alphabet = "abcdefghijklmnopqrstuwvxyz".toCharArray();
    private static Random r = new Random();
    private static ThreegramWorker threegramWorker;
    private static char[] key;
    private static String[] words;
    private static String[] cryptedWords;

    static {
        try {
            threegramWorker = new ThreegramWorker("C:\\Users\\limonpudding\\IdeaProjects\\PSU\\analyse_crypto\\3grams.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        x.put('a', 0.081716);
        x.put('b', 0.015979);
        x.put('c', 0.027389);
        x.put('d', 0.041704);
        x.put('e', 0.122352);
        x.put('f', 0.022916);
        x.put('g', 0.021081);
        x.put('h', 0.058286);
        x.put('i', 0.068545);
        x.put('j', 0.001982);
        x.put('k', 0.008695);
        x.put('l', 0.043247);
        x.put('m', 0.025913);
        x.put('n', 0.068793);
        x.put('o', 0.076513);
        x.put('p', 0.018749);
        x.put('q', 0.001112);
        x.put('r', 0.060362);
        x.put('s', 0.063354);
        x.put('t', 0.089239);
        x.put('u', 0.028798);
        x.put('v', 0.010077);
        x.put('w', 0.021125);
        x.put('x', 0.001781);
        x.put('y', 0.019296);
        x.put('z', 0.000996);
    }
    public static void main(String[] args) throws IOException {

        double[] nl_p = new double[]{0.081716, 0.015979, 0.027389, 0.041704, 0.122352, 0.022916, 0.021081, 0.058286, 0.068545, 0.001982, 0.008695, 0.043247, 0.025913, 0.068793, 0.076513, 0.018749, 0.001112, 0.060362, 0.063354, 0.089239, 0.028798, 0.010077, 0.021125, 0.001781, 0.019296, 0.000996};

        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\limonpudding\\IdeaProjects\\PSU\\analyse_crypto\\input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
        }
        String text = sb.toString(); // Считанный текст
        String lowerText = text.toLowerCase(); // Текст с буквами в нижнем регистре

//        Matrix sourceStats = getAlphabetStatsMatrix(lowerText); // Подсчёт вероятностей встречаемости букв считанного текста
//
//        sourceStats.printToConsole();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//
//        Matrix natural = new Matrix();
//        for (Map.Entry entry : x.entrySet()) {
//            char letter = (Character) entry.getKey();
//            natural.setProbabilityById(natural.getLetterId(letter), (double) entry.getValue());
//        } // Формирование вероятностей встречаемости букв естественного языка
//
//        Matrix sorted = sortMAtrix(sourceStats); // Сортировка вероятностей букв считанного текста
//        sorted.printToConsole();
//
//        Matrix transposed = new Matrix();
//        transposed.removeAll();
//
//        for (int i = 0; i < natural.size(); i++) {
//            transposed.addElement(natural.getLetterById(i), sorted.getProbabilityById(i));
//        } // Перестановка букв "ключа" в соответствии с вероятностями букв естественного языка
//
//        StringBuilder approximation = new StringBuilder();
//        for (int i = 0; i < lowerText.length(); i++) {
//            char c = lowerText.charAt(i);
//            if (natural.containsLetter(c)) {
//                approximation.append(transposed.getLetterById(sorted.getLetterId(c)));
//            } else {
//                approximation.append(c);
//            }
//        } // Первое приближение "ключа"
//        System.out.println(approximation);

        words = lowerText.split(" ");
        cryptedWords = lowerText.split(" ");

        double newScore;//threegramWorker.calculateScore(lowerText.toString());
        double score = -1000000;
        double bestScore = -1000000;
        double t = 1.;
        double freezing = 0.9997;
//        char[] key = transposed.getLetters();
        key = Arrays.copyOf(alphabet, alphabet.length);
        char[] testKey;
        long kek = 0;
        while (t > 0.001) {
            testKey = randomTranspose(key);
            kek++;
            newScore = score(testKey);
            if (newScore > score) {
                if (newScore > bestScore) {
                    bestScore = newScore;
                    for (String word:cryptedWords) {
                        System.out.print(word+" ");
                    }
                    System.out.println(kek);
                    System.out.println(
                            "TEMP: " + t + "\n" +
                            "SCORE: " + bestScore + "\n" +
                            "KEY: " + String.valueOf(testKey).toUpperCase() + "\n" +
                            "#-----------------------------------------------------------------------------------------------#\n");
                }
                score = newScore;
                key = testKey;
            } else {
                if (r.nextDouble() < t) {
                    score = newScore;
                    key = testKey;
                }
            }
            t *= freezing;
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                           "$                                              $\n" +
                           "$ THE BEST KEY IS: " + String.valueOf(key).toUpperCase() + "  $\n" +
                           "$                                              $\n" +
                           "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }

    private static double score(char[] key) {
        double score = 0;
        for (int i = 0; i < words.length; i++) {
            cryptedWords[i] = applySubstitute(words[i], key);
            score += threegramWorker.calculateScore(cryptedWords[i]);
        }
        return score;
    }

    private static char[] randomTranspose(char[] key) {
        char tmp1;
        char tmp2;
//        Random r = new Random();
        int r1 = r.nextInt(26);
        int r2 = r.nextInt(26);
//        while (r2 == r1) {
//            r2 = r.nextInt(26) + 12;
//            r2 = r2%26;
//        }
//        tmp = key[r1];
//        key[r1] = key[r2];
//        key[r2] = tmp;
        tmp1 = key[r1];
        tmp2 = key[r2];
        char[] generatedKey = new char[26];
        generatedKey = Arrays.copyOf(key, key.length);
        generatedKey[r1] = tmp2;
        generatedKey[r2] = tmp1;
        return generatedKey;
    }

    private static String applySubstitute(String text, char[] key) {
        Map<Character, Character> dict = new HashMap<>();
        for (int i = 0; i < alphabet.length; i++) {
            dict.put(alphabet[i], key[i]);
        }
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (dict.containsKey(c)) {
                decrypted.append(dict.get(c));
            } else {
                decrypted.append(c);
            }
        }
        return decrypted.toString();
    }

    private static Matrix sortMAtrix(Matrix sourceStats) {
        Matrix sorted = new Matrix();
        sorted.removeAll();
        while (sourceStats.size() > 0) {
            char maxLetter = sourceStats.getLetterById(0);
            double maxProbability = 0.;
            int id = 0;
            for (int i = 0; i < sourceStats.size(); i++) {
                double probability = sourceStats.getProbabilityById(i);
                if (probability > maxProbability) {
                    maxProbability = probability;
                    maxLetter = sourceStats.getLetterById(i);
                    id = i;
                }
            }

            sourceStats.removeById(id);

            if (maxProbability != -1) {
                sorted.addElement(maxLetter, maxProbability);
            }
        }
        return sorted;
    }

    private static Matrix getAlphabetStatsMatrix(String source) {
        Matrix counts = new Matrix();
        for (int i = 0; i < source.length(); i++) {
            char letter = source.charAt(i);
            int id = counts.getLetterId(letter);
            if (id > -1) {
                double p = counts.getProbability(letter) + 1;
                counts.changeProbabilityAtLetter(p, letter);
            }
        }
        double sum = 0.;
        for (int i = 0; i < counts.size(); i++) {
            sum += counts.getProbabilityById(i);
        }
        Matrix stats = new Matrix();
        for (int i = 0; i < counts.size(); i++) {
            double count = counts.getProbabilityById(i);
            stats.setProbabilityById(i, count / sum);
        }
        return stats;
    }
}