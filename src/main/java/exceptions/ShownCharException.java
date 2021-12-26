package exceptions;

public class ShownCharException extends Exception {
    private String message = "This character is already shown.";

    public ShownCharException() {
        super("This character is already shown");
    }

    public String getMessage() {
        return this.message;
    }
}
