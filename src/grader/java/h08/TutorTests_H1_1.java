package h08;

import h08.calculation.ArrayCalculatorWithPreconditions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h08")
@DisplayName("H1.1")
public class TutorTests_H1_1 {
    @Test
    @DisplayName("1 | Summenberechnung in ArrayCalculatorWithPreconditions ")
    // TODO: parametrisierter Test
    public void t01() {
        var sut = new ArrayCalculatorWithPreconditions();

        var array2 = new double[][]{
            new double[]{1},
            new double[]{1, 2},
            new double[]{1, -2, 3},
            new double[]{1, 2, 3, 4}
        };

        var actual = assertDoesNotThrow(() -> sut.addUp(array2, 50));
        assertEquals(10, actual);
    }
}
