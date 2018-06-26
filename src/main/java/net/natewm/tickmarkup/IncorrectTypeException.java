package net.natewm.tickmarkup;

public class IncorrectTypeException extends Exception {
    public IncorrectTypeException(String s) {
        super("Incorrect node type.  This is a " + s + ".");
    }
}
