package com.rogdanapp.stohastikalab1.data.pojo;

import android.content.Context;

import com.rogdanapp.stohastikalab1.R;

import java.io.InputStream;
import java.util.*;

public class Analyzer {
    private Context context;
    private Map<String, Integer> spamMap = new TreeMap<String, Integer>();
    private Map<String, Integer> hamMap = new TreeMap<String, Integer>();

    private ArrayList<AnalyzerItem> analyzedHam, analyzedSpam;

    private int spamCount, hamCount;

    public Analyzer(Context context) {
        this.context = context;

    }

    public void analysisData() {
        spamCount = 0;
        hamCount = 0;

        InputStream inputStream = null;
        try {
            inputStream = context.getResources().openRawResource(R.raw.english);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {
            String tmpLine = (scanner.nextLine()).toLowerCase();
            tmpLine = tmpLine.replaceAll("\\p{Digit}|\\p{Punct}", " ");

            Scanner scannerForLine = new Scanner(tmpLine);
            List<String> wordArr = new ArrayList<String>();
            while (scannerForLine.hasNext()) {
                String tmpWord = scannerForLine.next();
                if (tmpWord.length() > 2) {
                    switch (tmpWord) {
                        case "spam":
                            spamCount += wordArr.size();
                            for (int i = 0; i < wordArr.size(); i++) {
                                addToMap(wordArr.get(i), spamMap);
                            }

                            break;
                        case "ham":
                            hamCount += wordArr.size();
                            for (int i = 0; i < wordArr.size(); i++) {
                                addToMap(wordArr.get(i), hamMap);
                            }

                            break;
                        default:
                            wordArr.add(tmpWord);
                            break;
                    }
                }
            }
        }
        scanner.close();

        analyzedHam = convertToFrequencies(hamMap);
        analyzedSpam = convertToFrequencies(spamMap);
    }

    public ArrayList<AnalyzerItem> convertToFrequencies(Map<String, Integer> map) {
        ArrayList<AnalyzerItem> convertResult = new ArrayList<>();
        Map<Integer, String> result = new TreeMap<Integer, String>();

        for (String key : map.keySet()) {
            if (result.containsKey(map.get(key))) {
                result.put(map.get(key), result.get(map.get(key)) + ", " + key);
            } else {
                result.put(map.get(key), key);
            }
        }

        for (Integer key : result.keySet()) {
            AnalyzerItem item = new AnalyzerItem();
            item.setCount(key);
            float percent = 100 * ((float) key / (float) hamCount);
            item.setInPercent(percent);
            item.setLine(result.get(key));

            convertResult.add(item);
        }

        Collections.sort(convertResult);
        return convertResult;
    }

    public Map<String, Integer> getSpamMap() {
        return spamMap;
    }

    public Map<String, Integer> getHamMap() {
        return hamMap;
    }

    private void addToMap(String string, Map<String, Integer> map) {
        if (map.containsKey(string)) {
            map.put(string, map.get(string) + 1);
        } else {
            map.put(string, 1);
        }
    }

    public int getSpamCount() {
        return spamCount;
    }

    public void setSpamCount(int spamCount) {
        this.spamCount = spamCount;
    }

    public int getHamCount() {
        return hamCount;
    }

    public void setHamCount(int hamCount) {
        this.hamCount = hamCount;
    }

    public ArrayList<AnalyzerItem> getAnalyzedHam() {
        return analyzedHam;
    }

    public void setAnalyzedHam(ArrayList<AnalyzerItem> analyzedHam) {
        this.analyzedHam = analyzedHam;
    }

    public ArrayList<AnalyzerItem> getAnalyzedSpam() {
        return analyzedSpam;
    }

    public void setAnalyzedSpam(ArrayList<AnalyzerItem> analyzedSpam) {
        this.analyzedSpam = analyzedSpam;
    }
}
