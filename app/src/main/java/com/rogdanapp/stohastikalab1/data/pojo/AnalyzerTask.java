package com.rogdanapp.stohastikalab1.data.pojo;

import com.rogdanapp.stohastikalab1.tools.WordsFormatter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class AnalyzerTask {
    //Hard calculation. Recommended to call this method in background thread
    public BaesAnalyzeResult analyze(InputStream inputStream){
        BaesClass ham = new BaesClass(HAM_STOP_WORD);
        BaesClass spam = new BaesClass(SPAM_STOP_WORD);
        Scanner scanner = new Scanner(inputStream);
        HashMap<Word, Integer> tmpMap = new HashMap<>();

        while (scanner.hasNextLine()) {
            String stringList[] = WordsFormatter.format(scanner.nextLine());

            for (String next : stringList) {
                if (WordsFormatter.isWordValid(next)) {
                    switch (next) {
                        case SPAM_STOP_WORD:
                            spam.addParsedMessage(tmpMap);
                            tmpMap.clear();
                            break;

                        case HAM_STOP_WORD:
                            ham.addParsedMessage(tmpMap);
                            tmpMap.clear();
                            break;

                        default:
                            Word word = new Word(next);
                            addToMap(word, tmpMap);
                            break;
                    }
                }
            }
        }

        ham.calculateFrequency();
        spam.calculateFrequency();

        return new BaesAnalyzeResult(ham, spam);
    }

    private void addToMap(Word word, HashMap<Word, Integer> hashMap) {
        int count = 1;
        if (hashMap.containsKey(word)) {
            int oldCount = hashMap.get(word);
            count += oldCount;
        }

        hashMap.put(word, count);
    }

    private static final String SPAM_STOP_WORD = "spam";
    private static final String HAM_STOP_WORD = "ham";
}
