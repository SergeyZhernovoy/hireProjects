package com.altsoft;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] words = {"abc", "bac", "abc", "d","et","d","et","zzz"};
        Map<String, String> sorted = new HashMap<>();
        for (int index = 0; index < words.length; index++) {
            char[] chars = words[index].toCharArray();
            Arrays.sort(chars);
            String word = String.valueOf(chars);
            if (sorted.containsKey( word )) {
                String value = sorted.get( word );
                sorted.replace(word, value, value + "," + index );
            } else {
                sorted.put(word, " " + index);
            }
        }
        sorted.forEach((k,v) -> {
            if (v.length() > 2) {
                System.out.println(k + v);
            }
        });
    }
}
