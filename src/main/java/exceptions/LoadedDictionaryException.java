package exceptions;

public class LoadedDictionaryException extends Exception {
    private String message = "This dictionary is already loaded.";

    public LoadedDictionaryException() {
        super("This dictionary is already loaded.");
    }

    public String getMessage() {
        return this.message;
    }
}
