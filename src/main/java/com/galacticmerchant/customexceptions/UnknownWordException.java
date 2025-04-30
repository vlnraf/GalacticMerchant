package com.galacticmerchant.customexceptions;

public class UnknownWordException extends Exception {
    public UnknownWordException(String word) {
        super("Unknown word: " + word);
    }
}
