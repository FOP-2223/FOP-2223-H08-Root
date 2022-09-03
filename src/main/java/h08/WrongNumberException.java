package h08;

public class WrongNumberException extends Exception {
    public WrongNumberException(double number) {
        super(number + "");
    }
}
