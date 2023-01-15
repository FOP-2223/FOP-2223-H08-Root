package h08;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class ArrayCalculatorCtorReplacer implements ClassTransformer {
    @Override
    public String getName() {
        return "ArrayCalculatorCtorReplacer";
    }

    @Override
    public void transform(@NotNull ClassReader reader, ClassWriter writer) {
        if ("h08/Main".equals(reader.getClassName())) {
            reader.accept(new CV(writer), ClassReader.SKIP_DEBUG);
        } else {
            reader.accept(writer, ClassReader.SKIP_CODE);
        }
    }

    private static class CV extends ClassVisitor {
        public CV(ClassVisitor classVisitor) {
            super(Opcodes.ASM9, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if ("print".equals(name) && "([[DD)V".equals(descriptor)) {
                return new MV(super.visitMethod(access, name, descriptor, signature, exceptions));
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        private static class MV extends MethodVisitor {

            protected MV(MethodVisitor methodVisitor) {
                super(Opcodes.ASM9, methodVisitor);
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
                // replace creation of new instance
                if (opcode == Opcodes.NEW && "h08/calculation/ArrayCalculatorWithPreconditions".equals(type)) {
                    super.visitTypeInsn(opcode, "h08/MockArrayCalculatorWithPreconditions");
                } else {
                    super.visitTypeInsn(opcode, type);
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                // replace constructor invocation
                if (opcode == Opcodes.INVOKESPECIAL
                    && "h08/calculation/ArrayCalculatorWithPreconditions".equals(owner)
                    && "<init>".equals(name)
                    && "()V".equals(descriptor)) {
                    super.visitMethodInsn(opcode, "h08/MockArrayCalculatorWithPreconditions", name, descriptor, isInterface);
                } else {
                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }
    }
}
