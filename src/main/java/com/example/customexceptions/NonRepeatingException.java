package com.example.customexceptions;

public class NonRepeatingException extends Exception{
    public NonRepeatingException(Character character) {
        super("Non repeating character : " + character);
    }
}
