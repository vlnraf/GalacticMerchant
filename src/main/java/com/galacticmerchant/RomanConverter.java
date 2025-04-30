package com.galacticmerchant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.galacticmerchant.customexceptions.MaxRepeatingException;
import com.galacticmerchant.customexceptions.NonRepeatingException;

public class RomanConverter {

    private static final List<Character> NON_REPEATING_SYMBOLS = Arrays.asList('D', 'L', 'V');
    private static final List<Character> REPEATING_SYMBOLS = Arrays.asList('I', 'X', 'M', 'C');
    private static final int MAX_REPEATING = 3;
    private static final int MAX_NON_REPEATING = 1;

    private static final Map<Character, Integer> values = new HashMap<Character, Integer>() {
        {
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }
    };

    /**
     * Converts a Roman numeral string to a double value.
     * 
     * @param roman the Roman numeral string
     * @return numeric value of the Roman numeral
     * @throws MaxRepeatingException if a repeating symbol exceeds allowed limits
     * @throws NonRepeatingException if a non-repeating symbol is repeated
     */
    public static double convert(String roman) throws MaxRepeatingException, NonRepeatingException {
        double result = 0;
        int repeated = 1;

        for (int i = 0; i < roman.length(); i++) {
            if (i >= 1) {
                // Check for repeated symbols
                char last = roman.charAt(i - 1);
                char current = roman.charAt(i);
                repeated = (last == current) ? repeated + 1 : 1;

                // Validate repetition rules
                if (NON_REPEATING_SYMBOLS.contains(last) && repeated > MAX_NON_REPEATING)
                    throw new NonRepeatingException(last);
                if (REPEATING_SYMBOLS.contains(last) && repeated > MAX_REPEATING)
                    throw new MaxRepeatingException(last);
            }

            // Handle subtraction logic (e.g., IV = 4)
            if (i + 1 < roman.length() && values.get(roman.charAt(i)) < values.get(roman.charAt(i + 1))) {
                result -= values.get(roman.charAt(i));
            } else {
                result += values.get(roman.charAt(i));
            }
        }

        return result;
    }
}
