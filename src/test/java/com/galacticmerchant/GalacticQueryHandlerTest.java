package com.galacticmerchant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.galacticmerchant.customexceptions.UnknownWordException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GalacticQueryHandlerTest {

    private GalacticDictionary dictionary;
    private GalacticQueryHandler handler;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        dictionary = new GalacticDictionary();
        dictionary.addSymbol("glob", 'I');
        dictionary.addSymbol("prok", 'V');
        dictionary.setMetalValue("Silver", 17.0);
        handler = new GalacticQueryHandler(dictionary);

        System.setOut(new PrintStream(output));
    }

    @Test
    public void testHandleHowMuch() throws Exception {
        List<String> words = new ArrayList<>();
        words.add("glob");
        words.add("prok");
        handler.handleHowMuch(words);
        String result = output.toString().trim();
        assertTrue(result.contains("glob prok is 4"));
    }

    @Test
    public void testHandleHowMany() throws Exception {
        output.reset();
        List<String> words = new ArrayList<>();
        words.add("glob");
        words.add("glob");
        words.add("Silver");
        handler.handleHowMany(words);
        String result = output.toString().trim();
        assertTrue(result.contains("glob glob silver is 34.0 Credits"));
    }

    @Test
    public void testHandleDeclaration() throws Exception {
        GalacticDictionary dict = new GalacticDictionary();
        dict.addSymbol("glob", 'I');
        GalacticQueryHandler handler = new GalacticQueryHandler(dict);

        List<String> words = new ArrayList<>();
        words.add("glob");
        words.add("glob");
        words.add("Silver");
        words.add("is");
        words.add("34");
        words.add("Credits");
        handler.handleDeclarationMetal(words);

        assertTrue(dict.containMetal("Silver"));
        assertEquals(17.0, dict.getMetalValue("Silver"));
    }

    @Test
    public void testHandleDeclarationSymbol() {
        handler.handleDeclarationSymbol("pish", 'X');
        assertTrue(dictionary.containSymbol("pish"));
        assertEquals('X', dictionary.getRoman("pish"));
    }

    @Test
    public void testUnknownWordException() {
        List<String> words = new ArrayList<>();
        words.add("blah");
        words.add("blip");
        assertThrows(UnknownWordException.class, () ->
                handler.handleHowMuch(words));
    }
}
