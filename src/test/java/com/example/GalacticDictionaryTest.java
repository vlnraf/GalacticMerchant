package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GalacticDictionaryTest {

    @Test
    void testAddAndRetrieveSymbol() {
        GalacticDictionary dict = new GalacticDictionary();
        dict.addSymbol("glob", 'I');

        assertTrue(dict.containSymbol("glob"));
        assertEquals('I', dict.getRoman("glob"));
    }

    @Test
    void testUnknownSymbol() {
        GalacticDictionary dict = new GalacticDictionary();
        assertFalse(dict.containSymbol("blop"));
    }

    @Test
    void testAddAndGetMetalValue() {
        GalacticDictionary dict = new GalacticDictionary();
        dict.setMetalValue("Silver", 17.0);

        assertTrue(dict.containMetal("Silver"));
        assertEquals(17.0, dict.getMetalValue("Silver"));
    }

    @Test
    void testUnknownMetal() {
        GalacticDictionary dict = new GalacticDictionary();
        assertFalse(dict.containMetal("Gold"));
    }
}
