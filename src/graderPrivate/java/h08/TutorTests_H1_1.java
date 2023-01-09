package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.calculation.ArrayCalculatorWithRuntimeExceptions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

// DONE
@TestForSubmission
@DisplayName("H1.1")
public class TutorTests_H1_1 {
    @ParameterizedTest
    @DisplayName("Methode \"addUp\" berechnet die Summe korrekt.")
    @JsonClasspathSource("TutorTests_H1_1-addUpCalculatesSumCorrectly.json")
    public void addUpCalculatesSumCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode,
                                            @Property("expectedSum") double expectedSum) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        var sut = new ArrayCalculatorWithRuntimeExceptions();
        var actual = assertDoesNotThrow(() -> sut.addUp(testArray, Double.MAX_VALUE),
            "Die Funktion addUp wirft bei korrekten Eingabewerten eine unerwartete Exception.");
        assertEquals(expectedSum, actual, "Die Funktion addUp berechnet die Summe nicht korrekt.");
    }
}
