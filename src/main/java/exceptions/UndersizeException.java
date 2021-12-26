package exceptions;

public class UndersizeException extends Exception {
    private String message = "Not enough valid words";

    public UndersizeException() {
        super("Not enough valid words");
    }

    public String getMessage() {
        return this.message;
    }
}
