package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.calculation.ArrayCalculatorWithRuntimeExceptions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@TestForSubmission
@DisplayName("H1.2")
public class TutorTests_H1_2 {
    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"addUp\" wirft eine NullPointerException, wenn der Hauptarray null ist.")
    @ValueSource(doubles = {Double.MIN_VALUE, 0, Double.MAX_VALUE})
    public void addUpHandlesNullPrimaryArrayCorrectly(double max) {
        var sut = new ArrayCalculatorWithRuntimeExceptions();
        var thrownException = assertThrowsExactly(NullPointerException.class, () -> sut.addUp(null, max), "Die Methode addUp " +
            "wirft keine NullPointerException.");
        var actualMessage = thrownException.getMessage();
        assertEquals("Primary array is void!", actualMessage, "Die Botschaft der geworfenen Exception ist nicht korrekt.");
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"addUp\" wirft eine NullPointerException, wenn ein oder mehrere Einzelarrays null sind.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesNullSecondaryArrayCorrectly.json")
    public void addUpHandlesNullSecondaryArrayCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode, @Property("max"
    ) double max, @Property("expectedIndex") int expectedIndex) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        var sut = new ArrayCalculatorWithRuntimeExceptions();
        var thrownException = assertThrowsExactly(NullPointerException.class, () -> sut.addUp(testArray, max),
            "Die Methode addUp wirft keine NullPointerException.");
        var actualMessage = thrownException.getMessage();
        assertEquals(String.format("Secondary array at %s is void!", expectedIndex), actualMessage,
            "Die Botschaft der geworfenen Exception ist nicht korrekt.");
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"addUp\" wirft eine ArithmeticException, wenn max negativ ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesNegativeMaxCorrectly.json")
    public void addUpHandlesNegativeMaxCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode, @Property("max"
    ) double max) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        var sut = new ArrayCalculatorWithRuntimeExceptions();
        var thrownException = assertThrowsExactly(ArithmeticException.class, () -> sut.addUp(testArray, max),
            "Die Methode addUp wirft keine ArithmeticException.");
        var actualMessage = thrownException.getMessage();
        assertEquals("Upper bound is negative!", actualMessage,
            "Die Botschaft der geworfenen Exception ist nicht korrekt.");
    }

    @ParameterizedTest
    @DisplayName("Methode \"addUp\" wirft eine ArithmeticException, wenn max negativ ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesNegativeMaxCorrectly.json")
    public void addUpHandlesNegativeMaxCorrectly2(@Property("testArray") @NotNull ArrayNode testArrayNode, @Property("max"
    ) double max) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        var sut = new ArrayCalculatorWithRuntimeExceptions();
        var thrownException = assertThrowsExactly(ArithmeticException.class, () -> sut.addUp(testArray, max),
            "Die Methode addUp wirft keine ArithmeticException.");
        var actualMessage = thrownException.getMessage();
        assertEquals("Upper bound is negative!", actualMessage,
            "Die Botschaft der geworfenen Exception ist nicht korrekt.");
    }
}
