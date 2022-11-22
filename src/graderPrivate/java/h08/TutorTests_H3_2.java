package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.calculation.ArrayCalculatorWithPreconditions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// DONE
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

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    @DisplayName("Methode \"addUp\" verwendet die Preconditions-Klasse, um den ersten Ausnahmefall abzuprüfen.")
    public void addUpHandlesFirstCaseCorrectly() {
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

        var call =
            MockPreconditions.CheckPrimaryArrayNotNullInvocations.stream()
                .filter(i -> Arrays.deepEquals(testArray, i.primaryArray())).findFirst();

        assertTrue(call.isPresent(),
            "Die Methode addUp ruft CheckPrimaryArrayNotNull nicht korrekt auf.");

        assertEquals(0, call.get().index(),
            "Die Methode addUp ruft CheckPrimaryArrayNotNull zu spät auf.");
    }

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    @DisplayName("Methode \"addUp\" verwendet die Preconditions-Klasse, um den zweiten Ausnahmefall abzuprüfen.")
    public void addUpHandlesSecondCaseCorrectly() {
        MockPreconditions.reset();
        MockPreconditions.forwardInvocations = false;

        var testArray = new double[][]{
            {2344, 12313},
            {6384}
        };

        var sut = new ArrayCalculatorWithPreconditions();
        try {
            sut.addUp(testArray, 37645);
        } catch (Exception ignored) {
        }

        var call =
            MockPreconditions.CheckSecondaryArrayNotNullInvocations.stream()
                .filter(i -> Arrays.deepEquals(testArray, i.primaryArray())).findFirst();

        assertTrue(call.isPresent(),
            "Die Methode addUp ruft CheckSecondaryArrayNotNull nicht korrekt auf.");

        assertEquals(1, call.get().index(),
            "Die Methode addUp ruft CheckSecondaryArrayNotNull zu früh oder zu spät auf.");
    }

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    @DisplayName("Methode \"addUp\" verwendet die Preconditions-Klasse, um den dritten Ausnahmefall abzuprüfen.")
    public void addUpHandlesThirdCaseCorrectly() {
        MockPreconditions.reset();
        MockPreconditions.forwardInvocations = false;

        var testArray = new double[][]{
            {2344, 12313},
            {6384}
        };

        final double max = 634234;

        var sut = new ArrayCalculatorWithPreconditions();
        try {
            sut.addUp(testArray, max);
        } catch (Exception ignored) {
        }

        var call =
            MockPreconditions.CheckNumberNotNegativeInvocations.stream()
                .filter(i -> i.number() == max).findFirst();

        assertTrue(call.isPresent(),
            "Die Methode addUp ruft CheckNumberNotNegative nicht korrekt auf.");

        assertEquals(2, call.get().index(),
            "Die Methode addUp ruft CheckNumberNotNegative zu früh oder zu spät auf.");
    }

    // DONE
    @Test
    @ExtendWith(JagrExecutionCondition.class)
    @DisplayName("Methode \"addUp\" verwendet die Preconditions-Klasse, um den vierten Ausnahmefall abzuprüfen.")
    public void addUpHandlesFourthCaseCorrectly() {
        MockPreconditions.reset();
        MockPreconditions.forwardInvocations = false;

        var testArray = new double[][]{
            {2344, 12313},
            {6384}
        };

        final double max = 634234;

        var sut = new ArrayCalculatorWithPreconditions();
        try {
            sut.addUp(testArray, max);
        } catch (Exception ignored) {
        }

        var call =
            MockPreconditions.CheckValuesInRangeInvocations.stream()
                .filter(i -> Arrays.deepEquals(i.primaryArray(), testArray) && i.max() == max).findFirst();

        assertTrue(call.isPresent(),
            "Die Methode addUp ruft CheckValuesInRange nicht korrekt auf.");

        assertEquals(3, call.get().index(),
            "Die Methode addUp ruft CheckValuesInRange zu früh oder zu spät auf.");
    }

    // DONE
    @Test
    @DisplayName("Methode \"addUp\" deklariert eine WrongNumberException und eine AtIndexPairException mittels throws-Klausel.")
    @ExtendWith(TestCycleResolver.class)
    @ExtendWith(JagrExecutionCondition.class)
    public void checkAddUpDeclaresThrowsClauses(@NotNull TestCycle testCycle) {
        testCycle.getClassLoader().visitClass(ArrayCalculatorWithPreconditions.class.getName(), new CT());
    }

    public static class CT implements ClassTransformer {
        @Override
        public String getName() {
            return "H3_2-transformer";
        }

        @Override
        public void transform(@NotNull final ClassReader reader, final ClassWriter writer) {
            reader.accept(new CV(), 0);
        }

        private static class CV extends ClassVisitor {
            protected CV() {
                super(Opcodes.ASM9);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if ("addUp".equals(name) && "([[DD)D".equals(descriptor)) {
                    assertTrue(Arrays.asList(exceptions).contains("h08/preconditions/WrongNumberException"),
                        "Die Methode \"addUp\" deklariert keine WrongNumberException mittels throws-Klausel.");
                    assertTrue(Arrays.asList(exceptions).contains("h08/preconditions/AtIndexPairException"),
                        "Die Methode \"addUp\" deklariert keine AtIndexPairException mittels throws-Klausel.");
                }

                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
    }
}
