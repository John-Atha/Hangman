package exceptions;

public class NotFoundException extends Exception {
    public NotFoundException() {
        super("Could not fecth data, try another URL");
    }
    
}
