package com.rogdanapp.stohastikalab1.tools;

import com.rogdanapp.stohastikalab1.data.pojo.Word;

import java.util.ArrayList;
import java.util.HashSet;

public class WordsFormatter {
    private static HashSet<String> toIgnore = new HashSet<>();

    public static void init(String[] wordsToIgnore) {
        toIgnore = new HashSet<>();

        for (String word : wordsToIgnore) {
            if (!toIgnore.contains(word)) {
                toIgnore.add(word);
            }
        }
    }

    public static ArrayList<String> extractValidWords(String line) {
        ArrayList<String> result = new ArrayList<>();

        String[] formattedLine = format(line);
        for (String word : formattedLine) {
            if (isWordValid(word)) {
                result.add(word);
            }
        }

        return result;
    }

    public static String[] format(String line) {
        return line
                .toLowerCase()
                .replaceAll("’","")
                .replaceAll("\\p{Digit}|\\p{Punct}", " ")
                .trim()
                .split(" ");
    }

    public static boolean isWordValid(String word) {
        return hasNormalLength(word)
                && !toIgnore.contains(word);
                //&& hasLoudLetters(word);
    }

    /*any word must contain more then 3 letter*/
    private static boolean hasNormalLength(String possibleWord) {
        return possibleWord.length() >= MINIMAL_WORD_LENS;
    }

    /*any word must contain at least 1 loud letter*/
    private static boolean hasLoudLetters(String possibleWord) {
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
