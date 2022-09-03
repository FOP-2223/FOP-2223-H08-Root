package h08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class H08Tests {
    private H08 sut;

    @BeforeEach
    public void beforeEach() {
        sut = new H08();
    }

    @Test
    void addUpWithSelfDefinedExceptions1_validArray_returnsSum() {
        var array = new double[][] {
                new double[] { 1 },
                new double[] { 1, 2 },
                new double[] { 1, 2, 3 },
                new double[] { 1, 2, 3, 4 }
        };

        final double expectedSum = 20;
        double actualSum = 0;

        try {
            actualSum = sut.addUpWithSelfDefinedExceptions1(array, 4);
        } catch (Exception exception) {
            fail(exception);
        }

        assertEquals(expectedSum, actualSum);
    }

    @Test
    void addUpWithSelfDefinedExceptions1_arrayNull_throwsArrayIsNullException() {
        assertThrows(ArrayIsNullException.class, () -> sut.addUpWithSelfDefinedExceptions1(null, 42));
    }

    @Test
    void addUpWithSelfDefinedExceptions1_nullAtIndex2_throwsAtIndexException() {
        var array = new double[][] {
                new double[0],
                new double[0],
                null,
                new double[0]
        };

        AtIndexException thrownException = assertThrows(AtIndexException.class,
                () -> sut.addUpWithSelfDefinedExceptions1(array, 42));
        assertEquals("Index: 2", thrownException.getMessage());
    }

    @Test
    void addUpWithSelfDefinedExceptions1_maxIsNegative_throwsWrongNumberException() {
        var array = new double[0][];

        WrongNumberException thrownException = assertThrows(WrongNumberException.class,
                () -> sut.addUpWithSelfDefinedExceptions1(array, -1));
        assertEquals("-1.0", thrownException.getMessage());
    }

    @Test
    void addUpWithSelfDefinedExceptions1_numberNegative_throwsAtIndexPairException() {
        var array = new double[][] {
                new double[] { 1 },
                new double[] { 1, 2 },
                new double[] { 1, -2, 3 },
                new double[] { 1, 2, 3, 4 }
        };

        AtIndexPairException thrownException = assertThrows(AtIndexPairException.class,
                () -> sut.addUpWithSelfDefinedExceptions1(array, 42));
        assertEquals("Index: (2,1)", thrownException.getMessage());
    }
}
