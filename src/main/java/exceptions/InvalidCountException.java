package exceptions;

public class InvalidCountException extends Exception {
    private String message = "Duplicate words are not allowed";
    public InvalidCountException() {
        super("Duplicate words are not allowed");
    }

    public String getMessage() {
        return this.message;
    }
}
