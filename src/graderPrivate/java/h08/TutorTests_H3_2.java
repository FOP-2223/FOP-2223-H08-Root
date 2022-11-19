package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.calculation.ArrayCalculatorWithPreconditions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestForSubmission
@DisplayName("H3.2")
public class TutorTests_H3_2 {
    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"addUp\" berechnet die Summe korrekt.")
    @JsonClasspathSource("TutorTests_H1_1-addUpCalculatesSumCorrectly.json")
    public void addUpCalculatesSumCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode,
                                            @Property("expectedSum") double expectedSum) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        var sut = new ArrayCalculatorWithPreconditions();
        var actual = assertDoesNotThrow(() -> sut.addUp(testArray, Double.MAX_VALUE),
            "Die Funktion addUp wirft bei korrekten Eingabewerten eine unerwartete Exception.");
        assertEquals(expectedSum, actual, "Die Funktion addUp berechnet die Summe nicht korrekt.");
    }

    @Test
    @DisplayName("Methode \"addUp\" verwendet die Preconditions-Klasse, um den ersten Ausnahmefall abzuprüfen.")
    public void addUpHandlesNullPrimaryArrayCorrectly() {
        MockPreconditions.reset();
        MockPreconditions.forwardInvocations = false;

        var testArray = new double[][]{
            {2344, 12313},
            {6384}
        };

        var sut = new ArrayCalculatorWithPreconditions();
        try {
            sut.addUp(testArray, 42);
        } catch (Exception ignored) {
        }

        assertTrue(MockPreconditions.CheckPrimaryArrayNotNullInvocations.stream().anyMatch(i -> Arrays.deepEquals(i
                .primaryArray(), testArray)),
            "Die Methode addUp verwendet die Preconditions-Klasse nicht, um zu prüfen, ob der primary array null ist.");
    }
}
