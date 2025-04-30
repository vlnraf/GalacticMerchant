package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class QueryDispatcherTest {

    private QueryDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        dispatcher = new QueryDispatcher();
        dispatcher.parseQuery("glob is I");
        dispatcher.parseQuery("prok is V");
        dispatcher.parseQuery("glob glob Silver is 34 Credits");
    }

    @Test
    void testHowMuchAndHowManyQueries() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        dispatcher.parseQuery("how much is glob prok ?");
        assertTrue(out.toString().contains("glob prok is 4"));

        out.reset();

        dispatcher.parseQuery("how many Credits is glob glob Silver ?");
        assertTrue(out.toString().contains("glob glob silver is 34.0 Credits"));
    }
}
