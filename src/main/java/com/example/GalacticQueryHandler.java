package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.customexceptions.UnknownWordException;

public class GalacticQueryHandler {
    private final GalacticDictionary dictionary;

    public GalacticQueryHandler(GalacticDictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Handles "how much" queries by converting galactic symbols to Roman numerals,
     * then calculating and displaying their equivalent numeric value.
     *
     * Example: "how much is pish tegj glob glob ?" → "pish tegj glob glob is 42"
     *
     * @param words the galactic words representing a number
     * @throws Exception if conversion fails
     */
    public void handleHowMuch(String[] words) throws Exception {
        String roman = dictionary.toRoman(words);
        double value = RomanConverter.convert(roman);
        System.out.println(String.join(" ", words).toLowerCase() + " is " + value);
    }

    /**
     * Handles "how many Credits" queries. Converts galactic symbols to Roman numerals,
     * retrieves the unit value of a metal, and calculates the total credit value.
     *
     * Example: "how many Credits is glob prok Silver ?" → "glob prok silver is 68 Credits"
     *
     * @param words the galactic words followed by a metal name
     * @throws Exception if the word is unknown or conversion fails
     */
    public void handleHowMany(String[] words) throws Exception {
        List<String> symbolWords = new ArrayList<>();
        String metal = null;

        for (String word : words) {
            if (dictionary.containSymbol(word)) {
                symbolWords.add(word);
            } else if (dictionary.containMetal(word)) {
                metal = word;
                break;
            } else {
                throw new UnknownWordException(word);
            }
        }

        String roman = dictionary.toRoman(symbolWords.toArray(new String[0]));
        double units = RomanConverter.convert(roman);
        double credits = units * dictionary.getMetalValue(metal);

        System.out.println(String.join(" ", words).toLowerCase() + " is " + credits + " Credits");
    }

    /**
     * Handles declarations of metal values in credits.
     * Extracts galactic symbols and the metal, converts the symbols to a numeric value,
     * then calculates and stores the value of the metal per unit.
     *
     * Example: "glob glob Silver is 34 Credits"
     *
     * @param words the declaration sentence
     * @throws Exception if parsing or conversion fails
     */
    public void handleDeclarationMetal(String[] words) throws Exception {
        String metal = "";
        int credits = 0;
        List<String> symbolWords = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            if (dictionary.containSymbol(words[i])) {
                symbolWords.add(words[i]);
            } else if (words[i].equalsIgnoreCase("is")) {
                metal = words[i - 1];
                credits = Integer.parseInt(words[i + 1]);
                break;
            }
        }

        String roman = dictionary.toRoman(symbolWords.toArray(new String[0]));
        double units = RomanConverter.convert(roman);
        dictionary.setMetalValue(metal, credits / units);
    }

    /**
     * Handles declaration of new galactic symbol mappings.
     * Maps a galactic word to a Roman numeral.
     *
     * Example: "glob is I"
     *
     * @param word the galactic word
     * @param roman the corresponding Roman numeral
     */
    public void handleDeclarationSymbol(String word, char roman) {
        dictionary.addSymbol(word, roman);
    }
}
