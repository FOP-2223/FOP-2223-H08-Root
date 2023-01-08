package h08;

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

    // TODO: replace call to ArrayCalculatorWithPreconditions-constructor, to just test the print-method
    @Test
    @DisplayName("Methode \"print\" gibt bei korrekter Eingabe die Summe aus.")
    @ExtendWith(TestCycleResolver.class)
    @ExtendWith(JagrExecutionCondition.class)
    public void printOutputsSumForCorrectParameters(@NotNull TestCycle testCycle) {
        testCycle.getClassLoader().visitClass(Main.class.getName(), new ArrayCalculatorCtorReplacer());

        Main.print(new double[0][], 0);

        assertEquals("Sum: 42.0", outputStreamCaptor.toString().trim(),
            "Die Ausgabe der Methode \"print\" ist bei korrekter Berechnung der Summe nicht korrekt.");
    }
}
