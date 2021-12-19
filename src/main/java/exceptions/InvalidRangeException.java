package exceptions;

public class InvalidRangeException extends Exception {
    public InvalidRangeException() {
        super("Words with less than 6 letters are not allowed.");
    }    
}
