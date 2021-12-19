package exceptions;

public class InvalidCountException extends Exception {
    public InvalidCountException() {
        super("Duplicate words are not alowed");
    }
}
