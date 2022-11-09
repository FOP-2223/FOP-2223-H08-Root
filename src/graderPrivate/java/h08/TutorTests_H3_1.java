package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.preconditions.ArrayIsNullException;
import h08.preconditions.AtIndexException;
import h08.preconditions.AtIndexPairException;
import h08.preconditions.Preconditions;
import h08.preconditions.WrongNumberException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@TestForSubmission
@DisplayName("H3.1")
public class TutorTests_H3_1 {
    // checkPrimaryArrayNotNull

    // DONE
    @Test
    @DisplayName("Methode \"checkPrimaryArrayNotNull\" wirft eine ArrayIsNullException, wenn der Hauptarray null ist.")
    public void checkPrimaryArrayNotNullHandlesExceptionCaseCorrectly() {
        assertThrowsExactly(ArrayIsNullException.class,
            () -> Preconditions.checkPrimaryArrayNotNull(null),
            "Die Methode checkPrimaryArrayNotNull wirft keine ArrayIsNullException.");
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkPrimaryArrayNotNull\" wirft keine ArrayIsNullException, wenn der Hauptarray nicht null ist.")
    @JsonClasspathSource("TutorTests_H1_1-addUpCalculatesSumCorrectly.json")
    public void checkPrimaryArrayNotNullHandlesRegularCaseCorrectly1(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        checkPrimaryArrayNotNullHandlesRegularCaseCorrectlyCore(testArrayNode);
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkPrimaryArrayNotNull\" wirft keine ArrayIsNullException, wenn der Hauptarray nicht null ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesNullSecondaryArrayCorrectly.json")
    public void checkPrimaryArrayNotNullHandlesRegularCaseCorrectly2(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        checkPrimaryArrayNotNullHandlesRegularCaseCorrectlyCore(testArrayNode);
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkPrimaryArrayNotNull\" wirft keine ArrayIsNullException, wenn der Hauptarray nicht null ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesValuesOutOfRangeCorrectly.json")
    public void checkPrimaryArrayNotNullHandlesRegularCaseCorrectly3(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        checkPrimaryArrayNotNullHandlesRegularCaseCorrectlyCore(testArrayNode);
    }

    private void checkPrimaryArrayNotNullHandlesRegularCaseCorrectlyCore(@NotNull ArrayNode testArrayNode) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        assertDoesNotThrow(() -> Preconditions.checkPrimaryArrayNotNull(testArray),
            "Die Methode checkPrimaryArrayNotNull wirft eine unerwartete Exception.");
    }

    // checkSecondaryArraysNotNull

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkSecondaryArraysNotNull\" wirft eine AtIndexException, wenn ein Einzelarray null ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesNullSecondaryArrayCorrectly.json")
    public void checkSecondaryArraysNotNullHandlesExceptionCaseCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        assertThrowsExactly(AtIndexException.class,
            () -> Preconditions.checkSecondaryArraysNotNull(testArray),
            "Die Methode checkSecondaryArraysNotNull wirft keine AtIndexException.");
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkSecondaryArraysNotNull\" wirft keine AtIndexException, wenn kein Einzelarray null ist.")
    @JsonClasspathSource("TutorTests_H1_1-addUpCalculatesSumCorrectly.json")
    public void checkSecondaryArraysNotNullHandlesRegularCaseCorrectly1(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        checkSecondaryArraysNotNullHandlesRegularCaseCorrectlyCore(testArrayNode);
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkSecondaryArraysNotNull\" wirft keine AtIndexException, wenn kein Einzelarray null ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesValuesOutOfRangeCorrectly.json")
    public void checkSecondaryArraysNotNullHandlesRegularCaseCorrectly2(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        checkSecondaryArraysNotNullHandlesRegularCaseCorrectlyCore(testArrayNode);
    }

    private void checkSecondaryArraysNotNullHandlesRegularCaseCorrectlyCore(@NotNull ArrayNode testArrayNode) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        assertDoesNotThrow(() -> Preconditions.checkSecondaryArraysNotNull(testArray),
            "Die Methode checkSecondaryArraysNotNull wirft eine unerwartete Exception.");
    }

    // checkNumberNotNegative

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkNumberNotNegative\" wirft eine WrongNumberException, wenn max negativ ist.")
    @ValueSource(ints = {Integer.MIN_VALUE, -1367, -1})
    public void checkNumberNotNegativeHandlesExceptionCaseCorrectly(int number) {
        assertThrowsExactly(WrongNumberException.class,
            () -> Preconditions.checkNumberNotNegative(number),
            "Die Methode checkNumberNotNegative wirft keine WrongNumberException.");
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkNumberNotNegative\" wirft keine WrongNumberException, wenn max nicht negativ ist.")
    @ValueSource(ints = {Integer.MAX_VALUE, 12312, 1, 0})
    public void checkNumberNotNegativeHandlesRegularCaseCorrectly(int number) {
        assertDoesNotThrow(() -> Preconditions.checkNumberNotNegative(number),
            "Die Methode checkNumberNotNegative wirft eine unerwartete Exception.");
    }

    // checkValuesInRange

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkValuesInRange\" wirft eine AtIndexPairException, falls eine Komponente negativ oder größer als" +
        " max ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesValuesOutOfRangeCorrectly.json")
    public void checkValuesInRangeHandlesExceptionCaseCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode,
                                                                @Property("max") double max) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        assertThrowsExactly(AtIndexPairException.class,
            () -> Preconditions.checkValuesInRange(testArray, max),
            "Die Methode checkValuesInRange wirft keine AtIndexPairException.");
    }

    // TODO: check this. Integer.Max_Value might not be the best choice. Design dedignated json file for this test and the following tests
    @ParameterizedTest
    @DisplayName("Methode \"checkValuesInRange\" wirft keine AtIndexPairException, falls keine Komponente negativ oder größer " +
        "als" +
        " max ist.")
    @JsonClasspathSource("TutorTests_H1_1-addUpCalculatesSumCorrectly.json")
    public void checkValuesInRangeHandlesRegularCaseCorrectly1(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        checkValuesInRangeHandlesRegularCaseCorrectlyCore(testArrayNode, Integer.MAX_VALUE);
    }

    private void checkValuesInRangeHandlesRegularCaseCorrectlyCore(@NotNull ArrayNode testArrayNode, double max) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        assertDoesNotThrow(() -> Preconditions.checkValuesInRange(testArray, max),
            "Die Methode checkValuesInRange wirft eine unerwartete Exception.");
    }

    // TODO: check that constructors of exceptions are called with the correct parameters, but without checking the message
    //  that is set (use byte code transformer to check constructor call)
    // TODO: check throws-clauses and that they specify the exact exception instead of just Exception

}
