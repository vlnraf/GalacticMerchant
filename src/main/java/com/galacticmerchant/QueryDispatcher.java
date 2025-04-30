package com.galacticmerchant;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.galacticmerchant.customexceptions.MaxRepeatingException;
import com.galacticmerchant.customexceptions.NonRepeatingException;
import com.galacticmerchant.customexceptions.UnknownWordException;

public class QueryDispatcher {

    private final GalacticDictionary dictionary;
    private final GalacticQueryHandler queryHandler;

    public QueryDispatcher() {
        this.dictionary = new GalacticDictionary();
        this.queryHandler = new GalacticQueryHandler(dictionary);
    }

    public void runParser(String fileName){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                try {
                    parseQuery(line);
                } catch (UnknownWordException | MaxRepeatingException | NonRepeatingException e) {
                    System.out.println("Error processing line: \"" + line + "\"");
                    System.out.println("Reason: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Parses a given query string and delegates it to the appropriate handler
     * based on the structure and keywords.
     *
     * @param query the input query string
     * @throws Exception if any parsing or handling error occurs
     */
    public void parseQuery(String query) throws Exception {
        List<String> words = Arrays.asList(query.trim().split(" "));

        if (query.endsWith(Constants.queryCommand)) {
            if (query.startsWith(Constants.howMuch)) {
                handleHowMuchQuery(words);
            } else if (query.startsWith(Constants.howMany)) {
                handleHowManyQuery(words);
            } else {
                System.out.println(Constants.queryError);
            }
        }else{
            if (query.contains(Constants.declarationCommand)) {
                handleDeclarationMetal(words);
            }else if (checkDeclarativaQuerySymbol(words)) {
                handleDeclarationSymbol(words);
            }else{
                System.out.println(Constants.queryError);
            }
        }
    }

    /**
     * Handles "how much is" queries.
     * Example: "how much is pish tegj glob glob ?"
     */
    private void handleHowMuchQuery(List<String> words) throws Exception {
        // Skip "how much is"
        List<String> subWords = words.subList(3, words.size() - 1);

        queryHandler.handleHowMuch(subWords);
    }

    /**
     * Handles "how many Credits is" queries.
     * Example: "how many Credits is glob prok Silver ?"
     */
    private void handleHowManyQuery(List<String>  words) throws Exception {
        // Skip "how many Credits is"
        List<String> subWords = words.subList(4, words.size() - 1);
        queryHandler.handleHowMany(subWords);
    }

    /**
     * Handles declarations of metal values.
     * Example: "glob glob Silver is 34 Credits"
     */
    private void handleDeclarationMetal(List<String> words) throws Exception{
        queryHandler.handleDeclarationMetal(words);
    }

    /**
     * Handles declarations of galactic symbols.
     * Example: "glob is I"
     */
    private void handleDeclarationSymbol(List<String>  words) {
            queryHandler.handleDeclarationSymbol(words.get(0), words.get(2).charAt(0));
    }

    private boolean checkDeclarativaQuerySymbol(List<String>  words){
        if (words.size() == 3 && words.get(1).equalsIgnoreCase("is") && words.get(2).length() == 1) {
            return true;
        }else{
            return false;
        }
    }
}
