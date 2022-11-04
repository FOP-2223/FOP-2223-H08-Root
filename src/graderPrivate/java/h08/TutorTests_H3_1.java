package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.preconditions.ArrayIsNullException;
import h08.preconditions.AtIndexException;
import h08.preconditions.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@TestForSubmission
@DisplayName("H3.1")
public class TutorTests_H3_1 {
    // DONE
    @Test
    @DisplayName("Methode \"checkPrimaryArrayNotNull\" wirft eine ArrayIsNullException, wenn der Hauptarray null ist.")
    public void checkPrimaryArrayNotNullHandlesExceptionCaseCorrectly() {
        assertThrowsExactly(ArrayIsNullException.class,
            () -> Preconditions.checkPrimaryArrayNotNull(null),
            "Die Methode checkPrimaryArrayNotNull wirft keine ArrayIsNullException.");
    }

    // TODO: json ergänzen, durchchecken
    @ParameterizedTest
    @DisplayName("Methode \"checkPrimaryArrayNotNull\" wirft keine ArrayIsNullException, wenn der Hauptarray nicht null ist.")
    @JsonClasspathSource("TutorTests_H3_1-checkPrimaryArrayNotNullHandlesRegularCaseCorrectly.json")
    public void checkPrimaryArrayNotNullHandlesRegularCaseCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        assertDoesNotThrow(() -> Preconditions.checkPrimaryArrayNotNull(testArray),
            "Die Methode checkPrimaryArrayNotNull wirft eine unerwartete Exception.");
    }

    @ParameterizedTest
    @DisplayName("Methode \"checkSecondaryArraysNotNull\" wirft eine AtIndexException, wenn ein Einzelarray null ist.")
    @JsonClasspathSource("TutorTests_H3_1-checkSecondaryArraysNotNullHandlesExceptionCaseCorrectly.json")
    public void checkSecondaryArraysNotNullHandlesExceptionCaseCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode) {
        // TODO: verify constructor call, without verifying message, since that is part of another task

        assertThrowsExactly(AtIndexException.class,
            () -> Preconditions.checkSecondaryArraysNotNull(null),
            "Die Methode checkSecondaryArraysNotNull wirft keine AtIndexException.");
    }
}
