package exceptions;

public class NotFoundException extends Exception {
    private String message = "Invalid URL";
    public NotFoundException() {
        super("Invalid URL");
    }

    public String getMessage() {
        return this.message;
    }
}
