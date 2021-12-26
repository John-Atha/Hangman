package exceptions;

public class InvalidRangeException extends Exception {
    private String message = "Words with less than 6 letters are not allowed";

    public InvalidRangeException() {
        super("Words with less than 6 letters are not allowed");
    }

    public String getMessage() {
        return this.message;
    }
}
