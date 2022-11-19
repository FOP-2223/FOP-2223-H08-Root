package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.calculation.ArrayCalculatorWithPreconditions;
import h08.preconditions.AtIndexPairException;
import h08.preconditions.WrongNumberException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

        var testArray = new double[][]{
            {2344, 12313},
            {6384}
        };

        var sut = new ArrayCalculatorWithPreconditions();
        try {
            sut.addUp(testArray, 42);
        } catch (Exception ignored) {
        }

        assertTrue(MockPreconditions.CheckPrimaryArrayNotNullInvocations.stream().anyMatch(i -> Arrays.deepEquals(i.primaryArray(), testArray)),
            "Die Methode addUp verwendet die Preconditions-Klasse nicht, um zu prüfen, ob der primary array null ist.");
    }

    public static class CT implements ClassTransformer {
        private final String methodName;
        private final String descriptor;
        private final String expectedException;

        public CT(String methodName, String descriptor, String expectedException) {
            this.methodName = methodName;
            this.descriptor = descriptor;
            this.expectedException = expectedException;
        }

        @Override
        public String getName() {
            return "H3_2-transformer";
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
                public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                    if (opcode == Opcodes.INVOKESTATIC) {
                        if ("h08/preconditions/Preconditions".equals(owner) && "checkPrimaryArrayNotNull".equals(name) &&
                            "([[D)V".equals(descriptor)) {
                            // TODO: check ALOAD before and then verify this call? or use mocks somehow?
                        }
                    }

                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }
    }
}
