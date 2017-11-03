package com.rogdanapp.stohastikalab1.data.pojo;

import com.rogdanapp.stohastikalab1.tools.WordsFormatter;

import java.util.ArrayList;

public class BaesAnalyzeResult {
    private BaesClass hamResult, spamResult;

    public BaesAnalyzeResult(BaesClass hamResult, BaesClass spamResult) {
        this.hamResult = hamResult;
        this.spamResult = spamResult;
    }

    /**
     * @param message - message to check spam probability
     * @return value from [0 to 1]. Probability, that this message is SPAM.
     *      Therefore, probability, that value would be HAM = 1 - thisMethodResult
     */
    public double getSpamProbability(String message) {
        ArrayList<String> filtratedMessage = WordsFormatter.extractValidWords(message);

        double spamLn = calculateLog(spamResult, filtratedMessage);
        double hamLn = calculateLog(hamResult, filtratedMessage);

        return 1 / (1 + Math.exp(spamLn - hamLn));
    }

    private double calculateLog(BaesClass structure, ArrayList<String> filtratedPage) {
        int allMessageCount = hamResult.getMessageCount() + spamResult.getMessageCount();
        int uniqueWordsCount = hamResult.getClassUniqueWordsCount() + spamResult.getClassUniqueWordsCount();

        return Math.log((double) structure.getMessageCount() / (double)allMessageCount) + structure.calculateInnerLog(uniqueWordsCount, filtratedPage);
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