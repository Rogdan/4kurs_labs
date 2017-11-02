package com.rogdanapp.stohastikalab1.data.pojo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AnalyzerTask {
    private HashMap<Word, Integer> ham, spam;
    private int spamCount, hamCount;

    public AnalyzerTask() {
        reset();
    }

    //recommend to call this method in background thread
    public void analyze(InputStream inputStream){
        reset();

        Scanner scanner = new Scanner(inputStream);
        HashMap<Word, Integer> tmpMap = new HashMap<>();
        int tmpCounter = 0;

        while (scanner.hasNextLine()) {
            String stringList[] = format(scanner.nextLine());

            for (String next : stringList) {
                if (isWord(next)) {
                    switch (next) {
                        case SPAM_STOP_WORD:
                            spamCount += tmpCounter;
                            addAll(tmpMap, spam);
                            tmpMap.clear();
                            tmpCounter = 0;
                            break;

                        case HAM_STOP_WORD:
                            hamCount += tmpCounter;
                            addAll(tmpMap, ham);
                            tmpMap.clear();
                            tmpCounter = 0;
                            break;

                        default:
                            tmpCounter++;
                            Word word = new Word(next);
                            addToMap(word, 1, tmpMap);
                            break;
                    }
                }
            }
        }
    }

    private void reset() {
        ham = new HashMap<>();
        spam = new HashMap<>();
        spamCount = 0;
        hamCount = 0;
    }

    private void addAll(HashMap<Word, Integer> from, HashMap<Word, Integer> to) {
        for (Word word : from.keySet()) {
            int count = from.get(word);
            addToMap(word, count, to);
        }
    }

    private void addToMap(Word word, int count, HashMap<Word, Integer> hashMap) {
        if (hashMap.containsKey(word)) {
            int oldCount = hashMap.get(word);
            count += oldCount;
        }

        hashMap.put(word, count);
    }

    private String[] format(String text) {
        return text
                .toLowerCase()
                .replaceAll("’","")
                .replaceAll("\\p{Digit}|\\p{Punct}", " ")
                .trim()
                .split(" ");
    }

    private boolean isWord(String possibleWord) {
        return
                hasNormalLength(possibleWord) &&
                hasLoudLetter(possibleWord);
    }

    /*any word must contain more then 3 letter*/
    private boolean hasNormalLength(String possibleWord) {
        return possibleWord.length() >= MINIMAL_WORD_LENS;
    }

    /*any word must contain at least 1 loud letter*/
    private boolean hasLoudLetter(String possibleWord) {
        for (String loudLetter : LOUD_LETTERS) {
            if (possibleWord.contains(loudLetter)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<AnalyzerItem> getAnalyzedHam() {
        return groupToFrequencies(ham, hamCount);
    }

    public ArrayList<AnalyzerItem> getAnalyzedSpam() {
        return groupToFrequencies(spam, spamCount);
    }

    private ArrayList<AnalyzerItem> groupToFrequencies(Map<Word, Integer> map, int count) {
        ArrayList<AnalyzerItem> convertResult = new ArrayList<>();
        Map<Integer, String> groupedMap = new HashMap<>();

        for (Word word : map.keySet()) {
            int repeatCount = map.get(word);

            if (groupedMap.containsKey(repeatCount)) {
                groupedMap.put(repeatCount, groupedMap.get(repeatCount) + ", " + word.toString());
            } else {
                groupedMap.put(map.get(word), word.toString());
            }
        }

        for (Integer key : groupedMap.keySet()) {
            AnalyzerItem item = new AnalyzerItem();
            item.setCount(key);
            float percent = ONE_HUNDRED_PERCENT * ((float) key / (float) count);
            item.setInPercent(percent);
            item.setLine(groupedMap.get(key));

            convertResult.add(item);
        }

        Collections.sort(convertResult);
        return convertResult;
    }

    private static final String LOUD_LETTERS[] = {"a", "e", "i", "o", "u"};
    private static final int MINIMAL_WORD_LENS = 3;
    private static final String SPAM_STOP_WORD = "spam";
    private static final String HAM_STOP_WORD = "ham";
    private static final float ONE_HUNDRED_PERCENT = 100f;
}
