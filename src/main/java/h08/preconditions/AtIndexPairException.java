package h08.preconditions;

/**
 * Thrown when any value in a secondary array is outside the valid range.
 */
public class AtIndexPairException extends Exception {
    public AtIndexPairException(int i, int j) {
        super("Index: (" + i + "," + j + ")");
    }
}
