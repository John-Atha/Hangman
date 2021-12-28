package exceptions;

public class NoDictsException extends Exception {

    private String message = "There are not any loaded dictionaries.";

    public NoDictsException() {
        super("There are not any loaded dictionaries.");
    }

    public String getMessage() {
        return this.message;
    }
}
