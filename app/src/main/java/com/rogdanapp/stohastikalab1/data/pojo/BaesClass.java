package com.rogdanapp.stohastikalab1.data.pojo;

import com.rogdanapp.stohastikalab1.tools.Informator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BaesClass {
    private HashMap<Word, Integer> dataMap;
    private int messageCount;
    private int wordsCount;
    private ArrayList<AnalyzerItem> groupedFrequency;
    private String typeString;

    public BaesClass(String typeString) {
        this.typeString = typeString;
        reset();
    }

    private void reset() {
        dataMap = new HashMap<>();
        groupedFrequency = new ArrayList<>();
        messageCount = 0;
        wordsCount = 0;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public HashMap<Word, Integer> getDataMap() {
        return dataMap;
    }

    public double calculateInnerLn(int allUniqueWordsCount, ArrayList<String> filtratedWordsList) {
        double result = 0;
        for (String wordString : filtratedWordsList) {
            int wordRepeatingCount = 0;
            Word word = new Word(wordString);
            word.setSuffixes(new ArrayList<>());

            if (dataMap.containsKey(word)) {
                wordRepeatingCount = dataMap.get(word);
            }

            wordRepeatingCount++;

            result += Math.log((double) (wordRepeatingCount + 1) / ((double)allUniqueWordsCount + (double)wordsCount));
        }

        return result;
    }

    public void addParsedMessage(HashMap<Word, Integer> pageWords) {
        messageCount++;

        for (Word word : pageWords.keySet()) {
            int count = pageWords.get(word);
            wordsCount += count;
            addWordToMap(word, count);
        }
    }

    private void addWordToMap(Word word, int count) {
        if (dataMap.containsKey(word)) {
            int oldCount = dataMap.get(word);
            count += oldCount;
        }

        dataMap.put(word, count);
    }

    public ArrayList<AnalyzerItem> getGroupedFrequency() {
        if (groupedFrequency.isEmpty()) {
            calculateFrequency();
        }

        return groupedFrequency;
    }

    // групирует слова по частоте встречания в список, отсортированный по частоте
    public void calculateFrequency() {
        HashMap<Integer, String> groupedMap = groupWordsInMapByFrequency();
        groupedFrequency = parseToList(groupedMap);
        Collections.sort(groupedFrequency);
    }

    //Групирует слова в Map по частоте встречания в тексте
    private HashMap<Integer, String> groupWordsInMapByFrequency() {
        HashMap<Integer, String> groupedMap = new HashMap<>();

        for (Word word : dataMap.keySet()) {
            int repeatCount = dataMap.get(word);

            if (groupedMap.containsKey(repeatCount)) {
                groupedMap.put(repeatCount, groupedMap.get(repeatCount) + ", " + word.toString());
            } else {
                groupedMap.put(dataMap.get(word), word.toString());
            }
        }

        return groupedMap;
    }

    //парсит Map в отсортированный список
    private ArrayList<AnalyzerItem> parseToList(HashMap<Integer, String> groupedMap) {
        ArrayList<AnalyzerItem> groupedList = new ArrayList<>();

        for (Integer key : groupedMap.keySet()) {
            AnalyzerItem item = new AnalyzerItem();
            item.setCount(key);
            float percent = ONE_HUNDRED_PERCENT * ((float) key / (float) wordsCount);
            item.setInPercent(percent);
            item.setText(groupedMap.get(key));

            groupedList.add(item);
        }

        return groupedList;
    }

    public String getTypeString() {
        return typeString;
    }

    private static final float ONE_HUNDRED_PERCENT = 100f;
}