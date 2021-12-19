package exceptions;

public class UnbalancedException extends Exception {
    public UnbalancedException() {
        super("At least 20% of the words must have more than 8 characters.");
    }
    
}
