package h08;

public class AtIndexException extends Exception {
    public AtIndexException(int i) {
        super("Index: " + i);
    }
}
