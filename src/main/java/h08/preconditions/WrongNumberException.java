package h08.preconditions;

/**
 * Thrown when a max value is negative.
 */
public class WrongNumberException extends Exception {
    public WrongNumberException(double number) {
        super(number + "");
    }
}
