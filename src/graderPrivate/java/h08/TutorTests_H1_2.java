package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.calculation.ArrayCalculatorWithRuntimeExceptions;
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
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // DONE
    @ParameterizedTest
    @DisplayName("Methode \"addUp\" wirft eine ArithmeticException, wenn eine Komponente negativ oder größer als max ist.")
    @JsonClasspathSource("TutorTests_H1_2-addUpHandlesValuesOutOfRangeCorrectly.json")
    public void addUpHandlesValuesOutOfRangeCorrectly(@Property("testArray") @NotNull ArrayNode testArrayNode,
                                                      @Property("expectedIndexPair") @NotNull ExpectedIndexPair expectedIndexPair,
                                                      @Property("max") double max) {
        var converter = new ArrayNodeConverter(testArrayNode);
        var testArray = converter.convert();

        var sut = new ArrayCalculatorWithRuntimeExceptions();
        var thrownException = assertThrowsExactly(ArithmeticException.class, () -> sut.addUp(testArray, max),
            "Die Methode addUp wirft keine ArithmeticException.");
        var actualMessage = thrownException.getMessage();
        assertEquals(String.format("Value at (%s,%s) is not in range!", expectedIndexPair.i(), expectedIndexPair.j()),
            actualMessage, "Die Botschaft der geworfenen Exception ist nicht korrekt.");
    }

    // DONE
    @Test
    @DisplayName("Methode \"addUp\" verwendet maximal 4 throw-Anweisungen.")
    @ExtendWith(TestCycleResolver.class)
    public void addUpDoesNotExceedMaximumNumberOfThrowStatements(@NotNull TestCycle testCycle) {
        testCycle.getClassLoader().visitClass(ArrayCalculatorWithRuntimeExceptions.class.getName(), new Transformer());
    }

    public static class Transformer implements ClassTransformer {
        @Override
        public String getName() {
            return "Transformer";
        }

        @Override
        public void transform(final ClassReader reader, final ClassWriter writer) {
            reader.accept(new CV(), 0);
        }

        private static class CV extends ClassVisitor {
            protected CV() {
                super(Opcodes.ASM9);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if ("addUp".equals(name) && "([[DD)D".equals(descriptor)) {
                    return new MV();
                }

                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }

            private static class MV extends MethodVisitor {
                private int executedThrows = 0;

                protected MV() {
                    super(Opcodes.ASM9);
                }

                @Override
                public void visitInsn(int opcode) {
                    if (opcode == Opcodes.ATHROW) {
                        executedThrows++;
                        assertTrue(executedThrows <= 4, "Es wurden mehr als die maximal erlaubten 4 throw-Anweisungen verwendet" +
                            ".");
                    }

                    super.visitInsn(opcode);
                }
            }
        }
    }
}
