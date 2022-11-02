package h08.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class MainTransformer implements ClassTransformer {
    @Override
    public String getName() {
        return "Main-transformer";
    }

    @Override
    public void transform(final ClassReader reader, final ClassWriter writer) {
        reader.accept(new CV(writer), 0);
    }

    private static class CV extends ClassVisitor {
        private final ClassWriter writer;

        protected CV(ClassWriter writer) {
            super(Opcodes.ASM9, writer);
            this.writer = writer;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if ("print".equals(name) && "([[DD)V".equals(descriptor)) {
                return new MV(null);
            }

            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        private static class MV extends MethodVisitor {
            private final ClassWriter writer;

            protected MV(ClassWriter writer) {
                super(Opcodes.ASM9);
                this.writer = writer;
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
                if (opcode == Opcodes.NEW && "h08/calculation/ArrayCalculatorWithPreconditions".equals(type)) {
                    super.visitTypeInsn(opcode, "h08/calculation/ArrayCalculatorWithRuntimeExceptions");
                } else {
                    super.visitTypeInsn(opcode, type);
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if (opcode == Opcodes.INVOKESPECIAL && "h08/calculation/ArrayCalculatorWithPreconditions".equals(owner) &&
                    "<init>".equals(name) && "()V".equals(descriptor) && !isInterface) {
                    super.visitMethodInsn(opcode, "h08/calculation/ArrayCalculatorWithRuntimeExceptions", name, descriptor,
                        isInterface);
                } else {
                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }
    }
}
