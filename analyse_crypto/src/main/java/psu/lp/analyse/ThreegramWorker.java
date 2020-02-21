package psu.lp.analyse;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ThreegramWorker {

    private final int l = 3;
    private double undef = 0.;
    private Map<String, Integer> tgramsCount = new HashMap<>();
    private HashMap<String, Double> tgramsProbabilities = new HashMap<>();
    private long n = 0;

    ThreegramWorker(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        String line = br.readLine();
        while (line != null) {
            int spaceId = line.indexOf(' ');
            Integer count = Integer.valueOf(line.substring(spaceId + 1));
            tgramsCount.put(line.substring(0, spaceId), count);
            n += count;
            line = br.readLine();
        }
        for (Map.Entry entry : tgramsCount.entrySet()) {
            tgramsProbabilities.put((String) entry.getKey(), Math.log10((Integer) entry.getValue() / (double) n));
        }
        undef = Math.log10(0.01 / n);
    }

    public double calculateScore(String text) {
        double score = 0.;
        for (int i = 0; i < text.length() - 2; i++) {
            String tGram = text.substring(i, i + 3);
            if (tgramsProbabilities.containsKey(tGram)) {
                score += tgramsProbabilities.get(tGram);
            } else {
                score += undef;
            }
        }
        return score;
    }
}
