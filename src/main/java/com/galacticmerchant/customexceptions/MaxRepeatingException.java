package com.galacticmerchant.customexceptions;

public class MaxRepeatingException extends Exception{
    public MaxRepeatingException(Character character) {
        super("Character : " + character + " repeated more then 3 times");
    }
}
