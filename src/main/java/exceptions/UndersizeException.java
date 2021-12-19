package exceptions;

public class UndersizeException extends Exception { 
    public UndersizeException() {
        super("Not enough valid words.");
    }
}
