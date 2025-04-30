package com.galacticmerchant;

import org.junit.jupiter.api.Test;

import com.galacticmerchant.customexceptions.MaxRepeatingException;
import com.galacticmerchant.customexceptions.NonRepeatingException;

import static org.junit.jupiter.api.Assertions.*;

class RomanConverterTest {

    @Test
    void testValidRomanConversion() throws MaxRepeatingException, NonRepeatingException {
        assertEquals(4, RomanConverter.convert("IV"));
        assertEquals(1944, RomanConverter.convert("MCMXLIV"));
    }

    @Test
    void testMaxRepeatingException() {
        assertThrows(MaxRepeatingException.class, () -> RomanConverter.convert("IIII"));
    }

    @Test
    void testNonRepeatingException() {
        assertThrows(NonRepeatingException.class, () -> RomanConverter.convert("VV"));
    }
}
