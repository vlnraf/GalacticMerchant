package com.galacticmerchant;

import java.util.ArrayList;
import java.util.List;

import com.galacticmerchant.customexceptions.UnknownWordException;

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
    public void handleHowMuch(List<String> words) throws Exception {
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
    public void handleHowMany(List<String> words) throws Exception {
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

        String roman = dictionary.toRoman(symbolWords);
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
    public void handleDeclarationMetal(List<String> words) throws Exception {
        String metal = "";
        int credits = 0;
        List<String> symbolWords = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            if (dictionary.containSymbol(words.get(i))) {
                symbolWords.add(words.get(i));
            } else if (words.get(i).equalsIgnoreCase("is")) {
                metal = words.get(i - 1);
                credits = Integer.parseInt(words.get(i + 1));
                break;
            }
        }

        String roman = dictionary.toRoman(symbolWords);
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
