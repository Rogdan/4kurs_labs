package com.rogdanapp.stohastikalab1.data.pojo;

import java.io.InputStream;
import java.util.Scanner;

public class DeepWordAnalyzer {
    private InputStream inputStream;

    public DeepWordAnalyzer(InputStream fileInputStream) {
        inputStream = fileInputStream;
    }

    //recommend to call this method in background thread
    public void analyze(){
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNext()) {
            String next = scanner.next();
            next = next.replaceAll("\\p{Digit}|\\p{Punct}", "");
        }
    }

    private boolean isWord(String possibleWord) {
        return
                hasNormalLength(possibleWord) &&
                hasLoudLetter(possibleWord);
    }

    /*any word with sens must contain more then 3 letter*/
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

    private static final String LOUD_LETTERS[] = {"a", "e", "i", "o", "u"};
    private static final int MINIMAL_WORD_LENS = 3;
}
