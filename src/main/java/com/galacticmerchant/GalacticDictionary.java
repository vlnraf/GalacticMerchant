package com.galacticmerchant;

import java.util.HashMap;
import java.util.Map;

import com.galacticmerchant.customexceptions.UnknownWordException;

public class GalacticDictionary {

    // Map to store galactic word to Roman numeral mapping (e.g., "glob" -> 'I')
    private final Map<String, Character> symbols = new HashMap<>();

    // Map to store metal names and their unit value (e.g., "Silver" -> 17.0)
    private final Map<String, Double> metalValues = new HashMap<>();

    public void addSymbol(String word, char roman) {
        symbols.put(word, roman);
    }

    public boolean containSymbol(String word) {
        return symbols.containsKey(word);
    }

    public char getRoman(String word) {
        return symbols.get(word);
    }

    /**
     * Converts an array of galactic words into a Roman numeral string.
     *
     * @param words array of galactic words (e.g., ["glob", "prok"])
     * @return Roman numeral representation (e.g., "IV")
     * @throws UnknownWordException if any word is not recognized
     */
    public String toRoman(String[] words) throws UnknownWordException {
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            if (!containSymbol(word)) {
                throw new UnknownWordException(word);
            }
            builder.append(getRoman(word));
        }
        return builder.toString();
    }

    public void setMetalValue(String metal, double value) {
        metalValues.put(metal, value);
    }

    public boolean containMetal(String word) {
        return metalValues.containsKey(word);
    }

    public double getMetalValue(String metal) {
        return metalValues.get(metal);
    }
}
