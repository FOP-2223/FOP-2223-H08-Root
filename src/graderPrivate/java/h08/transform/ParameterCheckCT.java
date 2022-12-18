package h08.transform;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class ParameterCheckCT implements ClassTransformer {

    public static Object[] Foobar;

    @Override
    public String getName() {
        return "H3_1-ParameterCheckCT";
    }

    @Override
    public int getWriterFlags() {
        return ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals("h08/preconditions/Preconditions")) {
            reader.accept(new CV(writer), ClassReader.SKIP_DEBUG);
        } else {
            reader.accept(writer, ClassReader.SKIP_CODE);
        }
    }

    public static class CV extends ClassVisitor {

        protected CV(ClassVisitor classVisitor) {
            super(Opcodes.ASM9, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if ("checkSecondaryArraysNotNull".equals(name) && "([[D)V".equals(descriptor)) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (opcode == Opcodes.INVOKESPECIAL
                            && owner.equals("h08/preconditions/AtIndexException")
                            && name.equals("<init>")
                            && descriptor.equals("(I)V")) {
                            ParameterInterceptor interceptor = new ParameterInterceptor(this);
                            interceptor.interceptParameters(Type.getArgumentTypes(descriptor));
                            interceptor.storeArrayRefInField("h08/transform/ParameterCheckCT", "Foobar");
                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }
                };
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
    }
}
