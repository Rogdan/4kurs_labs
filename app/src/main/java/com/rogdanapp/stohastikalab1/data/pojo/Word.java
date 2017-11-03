package com.rogdanapp.stohastikalab1.data.pojo;

import java.util.ArrayList;

public class Word {
   private String root;
   private ArrayList<String> suffixes;

   public Word(String word) {
      suffixes = new ArrayList<>();
      root = extractRoot(word);
   }

   private String extractRoot(String word) {
      for (String suffix : EN_SUFFIXES) {
         if (word.endsWith(suffix)) {
            return extractSuffix(word, suffix);
         }
      }

      return word;
   }

   private String extractSuffix(String word, String suffix){
      suffixes.add(suffix);
      int sufLength = suffix.length();
      int wordLength = word.length();

      return word.substring(0, wordLength - sufLength);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Word word = (Word) o;
      if (root.equals(word.getRoot())) {
         word.concat(this);
         return root.equals(word.getRoot());
      }

      return false;
   }

   @Override
   public int hashCode() {
      return root.hashCode();
   }

   public void concat(Word word) {
      for (String suffixToCheck : word.suffixes) {
         if (!suffixes.contains(suffixToCheck)) {
            suffixes.add(suffixToCheck);
         }
      }
   }

   public String getRoot() {
      return root;
   }


   public ArrayList<String> getSuffixes() {
      return suffixes;
   }

   public void setSuffixes(ArrayList<String> suffixes) {
      this.suffixes = suffixes;
   }

   @Override
   public String toString() {
      if (!suffixes.isEmpty()) {
         return toStringWithSuffixes();
      }

      return root;
   }

   private String toStringWithSuffixes() {
      StringBuilder result = new StringBuilder();
      boolean hasManySuffixes = suffixes.size() >= 2;

      if (hasManySuffixes) {
         result.append("[");

         for (int i = 0; i < suffixes.size() - 1; i++) {
            String suffix = suffixes.get(i);
            result.append(root).append(suffix).append(" | ");
         }
      }

      String lastSuffix = suffixes.get(suffixes.size() - 1);
      result.append(root).append(lastSuffix);

      if (hasManySuffixes) {
         result.append("]");
      }

      return result.toString();
   }

   private static final String EN_SUFFIXES[] = {"ing", "ed", "es", "s"};

}
