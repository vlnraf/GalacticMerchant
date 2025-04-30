package com.galacticmerchant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.galacticmerchant.GalacticDictionary;
import com.galacticmerchant.GalacticQueryHandler;
import com.galacticmerchant.customexceptions.UnknownWordException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        handler.handleHowMuch(new String[]{"glob", "prok"});
        String result = output.toString().trim();
        assertTrue(result.contains("glob prok is 4"));
    }

    @Test
    public void testHandleHowMany() throws Exception {
        output.reset();
        handler.handleHowMany(new String[]{"glob", "glob", "Silver"});
        String result = output.toString().trim();
        assertTrue(result.contains("glob glob silver is 34.0 Credits"));
    }

    @Test
    public void testHandleDeclaration() throws Exception {
        GalacticDictionary dict = new GalacticDictionary();
        dict.addSymbol("glob", 'I');
        GalacticQueryHandler handler = new GalacticQueryHandler(dict);

        handler.handleDeclarationMetal(new String[]{"glob", "glob", "Silver", "is", "34", "Credits"});

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
        assertThrows(UnknownWordException.class, () ->
                handler.handleHowMuch(new String[]{"blah", "blip"}));
    }
}
