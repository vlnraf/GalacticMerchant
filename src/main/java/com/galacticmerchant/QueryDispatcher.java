package com.galacticmerchant;

import java.util.Arrays;

public class QueryDispatcher {

    private final GalacticDictionary dictionary;
    private final GalacticQueryHandler queryHandler;

    public QueryDispatcher() {
        this.dictionary = new GalacticDictionary();
        this.queryHandler = new GalacticQueryHandler(dictionary);
    }

    /**
     * Parses a given query string and delegates it to the appropriate handler
     * based on the structure and keywords.
     *
     * @param query the input query string
     * @throws Exception if any parsing or handling error occurs
     */
    public void parseQuery(String query) throws Exception {
        String[] words = query.trim().split(" ");

        if (query.endsWith(Constants.queryCommand)) {
            if (query.startsWith(Constants.howMuch)) {
                handleHowMuchQuery(words);
            } else if (query.startsWith(Constants.howMany)) {
                handleHowManyQuery(words);
            } else {
                System.out.println(Constants.queryError);
            }
        } else if (query.contains(Constants.declarationCommand)) {
            handleDeclarationMetal(words);
        } else if (!query.contains(Constants.declarationCommand)){
            handleDeclarationSymbol(words);
        }else{
            System.out.println(Constants.queryError);
        }
    }

    /**
     * Handles "how much is" queries.
     * Example: "how much is pish tegj glob glob ?"
     */
    private void handleHowMuchQuery(String[] words) throws Exception {
        // Skip "how much is"
        String[] subWords = Arrays.copyOfRange(words, 3, words.length - 1);
        queryHandler.handleHowMuch(subWords);
    }

    /**
     * Handles "how many Credits is" queries.
     * Example: "how many Credits is glob prok Silver ?"
     */
    private void handleHowManyQuery(String[] words) throws Exception {
        // Skip "how many Credits is"
        String[] subWords = Arrays.copyOfRange(words, 4, words.length - 1);
        queryHandler.handleHowMany(subWords);
    }

    /**
     * Handles declarations of metal values.
     * Example: "glob glob Silver is 34 Credits"
     */
    private void handleDeclarationMetal(String[] words) throws Exception{
        queryHandler.handleDeclarationMetal(words);
    }

    /**
     * Handles declarations of galactic symbols.
     * Example: "glob is I"
     */
    private void handleDeclarationSymbol(String[] words) {
        if (words.length == 3 && words[1].equalsIgnoreCase("is") && words[2].length() == 1) {
            queryHandler.handleDeclarationSymbol(words[0], words[2].charAt(0));
        }
    }
}
