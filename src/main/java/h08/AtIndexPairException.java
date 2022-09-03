package h08;

public class AtIndexPairException extends Exception {
    public AtIndexPairException(int i, int j) {
        super("Index: (" + i + "," + j + ")");
    }
}
