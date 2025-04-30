package com.example.customexceptions;

public class UnknownWordException extends Exception {
    public UnknownWordException(String word) {
        super("Unknown word: " + word);
    }
}
