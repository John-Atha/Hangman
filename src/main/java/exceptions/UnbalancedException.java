package exceptions;

public class UnbalancedException extends Exception {
    private String message="At least 20% of the words must have more than 8 characters";

    public UnbalancedException() {
        super("At least 20% of the words must have more than 8 characters");
    }

    public String getMessage() {
        return this.message;
    }
    
}
