package com.rogdanapp.stohastikalab1.data.pojo.naive_bayes;

import com.rogdanapp.stohastikalab1.tools.WordsFormatter;

import java.util.ArrayList;
import java.util.HashMap;

public class BaesAnalyzeResult {
    private int uniqueWordsCount;
    private BaesClass hamResult, spamResult;

    public BaesAnalyzeResult(BaesClass hamResult, BaesClass spamResult) {
        this.hamResult = hamResult;
        this.spamResult = spamResult;

        calculateUiqueWordsCount();
    }

    private void calculateUiqueWordsCount() {
        HashMap<Word, Integer> hamWords = hamResult.getDataMap();
        uniqueWordsCount = hamWords.size();

        final HashMap<Word, Integer> spamWords = spamResult.getDataMap();

        for (Word word : hamWords.keySet()) {
            if (!spamWords.containsKey(word)) {
                uniqueWordsCount++;
            }
        }
    }

    /**
     * @param message - message to check spam probability
     * @return value from [0 to 1]. Probability, that this message is SPAM.
     *      Therefore, probability, that value would be HAM = 1 - thisMethodResult
     */
    public double getSpamProbability(String message) {
        ArrayList<String> filtratedMessage = WordsFormatter.extractValidWords(message);

        double spamLn = calculateLn(spamResult, filtratedMessage);
        double hamLn = calculateLn(hamResult, filtratedMessage);

        return 1 / (1 + Math.exp(hamLn - spamLn));
    }

    private double calculateLn(BaesClass structure, ArrayList<String> filtratedPage) {
        int allMessageCount = hamResult.getMessageCount() + spamResult.getMessageCount();

        return Math.log((double) structure.getMessageCount() / (double)allMessageCount) + structure.calculateInnerLn(uniqueWordsCount, filtratedPage);
    }

    public BaesClass getHamResult() {
        return hamResult;
    }

    public void setHamResult(BaesClass hamResult) {
        this.hamResult = hamResult;
    }

    public BaesClass getSpamResult() {
        return spamResult;
    }

    public void setSpamResult(BaesClass spamResult) {
        this.spamResult = spamResult;
    }
}