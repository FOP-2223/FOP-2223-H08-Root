package h08;

import h08.preconditions.ArrayIsNullException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

// DONE
@TestForSubmission
@DisplayName("H4")
public class TutorTests_H4 {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }


    // DONE
    @Test
    @DisplayName("Methode \"print\" gibt bei korrekter Eingabe die Summe aus.")
    @ExtendWith(JagrExecutionCondition.class)
    public void printOutputsSumForCorrectParameters() {
        double[][] array = {
            {1.0, 2.0, 3.0},
            {4.0, 52.0, 6.0},
            {7.0, 8.0, 9.0}
        };

        Main.print(array, 100);

        assertEquals("Sum: 92.0", outputStreamCaptor.toString().trim(),
            "Die Ausgabe der Methode \"print\" ist bei korrekter Berechnung der Summe nicht korrekt.");
    }

     // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void printOutputsBadArrayForAtIndexException() {
        double[][] array = {
            {1.0, 2.0, 3.0},
            null,
            {7.0, 8.0, 9.0}
        };

        Main.print(array, 100);

        assertEquals("Bad array: Index: 1", outputStreamCaptor.toString().trim(),
            "Die Ausgabe der Methode \"print\" ist beim Wurf einer AtIndexException nicht korrekt.");
    }

     // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void printOutputsBadArrayForAtIndexPairException() {
        double[][] array = {
            {1.0, 2.0, 3.0},
            {7.0, -1.0, 9.0}
        };

        Main.print(array, 100);

        assertEquals("Bad array: Index: (1,1)", outputStreamCaptor.toString().trim(),
            "Die Ausgabe der Methode \"print\" ist beim Wurf einer AtIndexPairException nicht korrekt");
    }

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void printOutputsBadMaxValueForWrongNumberException() {
        double[][] array = {
            {1.0, 2.0, 3.0},
            {7.0, 1.0, 9.0}
        };

        Main.print(array, -23);

        assertEquals("Bad max value: -23.0", outputStreamCaptor.toString().trim(),
            "Die Ausgabe der Methode \"print\" ist beim Wurf einer WrongNumberException nicht korrekt");
    }

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void printDoesNotCatchOtherExceptions() {
        assertThrowsExactly(ArrayIsNullException.class, () -> Main.print(null, -23),
            "Die Methode \"print\" wirft keine ArrayIsNullException, wenn die \"addUp\"-Methode des \"ArrayCalculatorWithPreconditions\"-Objekts eine ArrayIsNullException wirft.");
    }
}
