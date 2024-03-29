package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.preconditions.ArrayIsNullException;
import h08.preconditions.AtIndexException;
import h08.preconditions.AtIndexPairException;
import h08.preconditions.Preconditions;
import h08.preconditions.WrongNumberException;
import h08.transform.ParameterCheckCT;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.fail;
import static org.tudalgo.algoutils.tutor.general.TutorAssertions.call;

// DONE
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

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    @DisplayName("Methode \"checkSecondaryArraysNotNull\" erzeugt die AtIndexException mithilfe der korrekten Parameter.")
    public void checkSecondaryArraysNotNullUsesCorrectParameters() {
        double[][] array = {
            {1.0, 2.0, 3.0},
            {4.0, 5.0, 6.0},
            null,
            {7.0, 8.0, 9.0}
        };

        ParameterCheckCT.Foobar = null;
        call(() -> Preconditions.checkSecondaryArraysNotNull(array));

        assertEquals(2, ParameterCheckCT.Foobar[0],
            "Die Methode \"checkSecondaryArraysNotNull\" verwendet bei der Instanziierung der AtIndexException den falschen" +
                " Index.");
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

    // DONE
    @Test
    @DisplayName("Methode \"checkNumberNotNegative\" deklariert eine WrongNumberException mittels throws-Klausel.")
    @ExtendWith(TestCycleResolver.class)
    @ExtendWith(JagrExecutionCondition.class)
    public void checkNumberNotNegativeDeclaresThrowsClause(@NotNull TestCycle testCycle) {
        testCycle.getClassLoader().visitClass(Preconditions.class.getName(),
            new ThrowsClauseCheckCT("checkNumberNotNegative", "(D)V", "h08/preconditions/WrongNumberException"));
    }

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    @DisplayName("Methode \"checkNumberNotNegative\" erzeugt die WrongNumberException mithilfe der korrekten Parameter.")
    public void checkNumberNotNegativeUsesCorrectParameters() {
        final var expectedParameter = -423421d;
        ParameterCheckCT.Foobar = null;
        call(() -> {
            try {
                Preconditions.checkNumberNotNegative(expectedParameter);
            } catch (WrongNumberException ignored) {
                // We expect an exception to be thrown, but it is not relevant
            }
        });

        assertEquals(expectedParameter, ParameterCheckCT.Foobar[0],
            "Die Methode \"checkNumberNotNegative\" verwendet bei der Instanziierung der WrongNumberException die falsche Zahl " +
                "als Parameter.");
    }

    // checkValuesInRange

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkValuesInRange\" wirft eine AtIndexPairException, falls eine Komponente negativ oder größer als" +
        " " +
        "max ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesValuesOutOfRangeCorrectly.json")
    public void checkValuesInRangeHandlesExceptionCaseCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode,
                                                                @Property("max") double max) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        assertThrowsExactly(AtIndexPairException.class,
            () -> Preconditions.checkValuesInRange(testArray, max),
            "Die Methode checkValuesInRange wirft keine AtIndexPairException.");
    }

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"checkValuesInRange\" wirft keine AtIndexPairException, falls keine Komponente negativ oder größer " +
        "als max ist.")
    @JsonClasspathSource("TutorTests_H3_1_checkValuesInRangeHandlesRegularCaseCorrectly.json")
    public void checkValuesInRangeHandlesRegularCaseCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode,
                                                              @Property("max") double max) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        assertDoesNotThrow(() -> Preconditions.checkValuesInRange(testArray, max),
            "Die Methode checkValuesInRange wirft eine unerwartete Exception.");
    }

    // DONE
    @Test
    @DisplayName("Methode \"checkValuesInRange\" deklariert eine AtIndexPairException mittels throws-Klausel.")
    @ExtendWith(TestCycleResolver.class)
    @ExtendWith(JagrExecutionCondition.class)
    public void checkValuesInRangeDeclaresThrowsClause(@NotNull TestCycle testCycle) {
        testCycle.getClassLoader().visitClass(Preconditions.class.getName(),
            new ThrowsClauseCheckCT("checkValuesInRange", "([[DD)V", "h08/preconditions/AtIndexPairException"));
    }

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    @DisplayName("Methode \"checkValuesInRange\" erzeugt die AtIndexPairException mithilfe der korrekten Parameter.")
    public void checkValuesInRangeUsesCorrectParameters() {
        double[][] array = {
            {1.0, 2.0, 3.0},
            {4.0, 5.0, 6.0},
            {7.0, 44.0, 44.0},
            {10.0, 11.0, 12.0},
        };

        ParameterCheckCT.Foobar = null;
        call(() -> {
            try {
                Preconditions.checkValuesInRange(array, 40);
            } catch (AtIndexPairException ignored) {
                // We expect an exception to be thrown, but it is not relevant
            }
        });

        assertEquals(2, ParameterCheckCT.Foobar[0],
            "Die Methode \"checkValuesInRange\" verwendet bei der Instanziierung der AtIndexPairException den falschen" +
                " i Index.");
        assertEquals(1, ParameterCheckCT.Foobar[1],
            "Die Methode \"checkValuesInRange\" verwendet bei der Instanziierung der AtIndexPairException den falschen" +
                " j Index.");
    }

    public static class ThrowsClauseCheckCT implements ClassTransformer {
        private final String methodName;
        private final String descriptor;
        private final String expectedException;

        public ThrowsClauseCheckCT(String methodName, String descriptor, String expectedException) {
            this.methodName = methodName;
            this.descriptor = descriptor;
            this.expectedException = expectedException;
        }

        @Override
        public String getName() {
            return "H3_1-transformer";
        }

        @Override
        public void transform(@NotNull final ClassReader reader, final ClassWriter writer) {
            reader.accept(new CV(methodName, descriptor, expectedException), 0);
        }

        private static class CV extends ClassVisitor {
            private final String methodName;
            private final String descriptor;
            private final String expectedException;

            protected CV(String methodName, String descriptor, String expectedException) {
                super(Opcodes.ASM9);
                this.methodName = methodName;
                this.descriptor = descriptor;
                this.expectedException = expectedException;
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                             String[] exceptions) {
                if (methodName.equals(name) && this.descriptor.equals(descriptor)) {
                    var message = String.format(
                        "Die Methode %s muss eine %s mittels genau einer throws-Klausel deklarieren.",
                        methodName,
                        expectedException);

                    if (exceptions == null || exceptions.length != 1) {
                        fail(message);
                    }

                    assertEquals(expectedException, exceptions[0], message);
                }

                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
    }
}
