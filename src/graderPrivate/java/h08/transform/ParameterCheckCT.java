package h08.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class ParameterCheckCT implements ClassTransformer {
    private final String preconditionsMethodName;
    private final String preconditionsMethodDescriptor;
    private final String exceptionName;
    private final String exceptionConstructorDescriptor;
    public static Object[] Foobar;

    public ParameterCheckCT(String preconditionsMethodName, String preconditionsMethodDescriptor, String exceptionName,
                            String exceptionConstructorDescriptor) {
        this.preconditionsMethodName = preconditionsMethodName;
        this.preconditionsMethodDescriptor = preconditionsMethodDescriptor;
        this.exceptionName = exceptionName;
        this.exceptionConstructorDescriptor = exceptionConstructorDescriptor;
    }

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
            reader.accept(new CV(writer, preconditionsMethodName, preconditionsMethodDescriptor, exceptionName,
                exceptionConstructorDescriptor), ClassReader.SKIP_DEBUG);
        } else {
            reader.accept(writer, ClassReader.SKIP_CODE);
        }
    }

    public static class CV extends ClassVisitor {
        private final String preconditionsMethodName;
        private final String preconditionsMethodDescriptor;
        private final String exceptionName;
        private final String exceptionConstructorDescriptor;


        protected CV(ClassVisitor classVisitor, String preconditionsMethodName, String preconditionsMethodDescriptor, String exceptionName, String exceptionConstructorDescriptor) {
            super(Opcodes.ASM9, classVisitor);
            this.preconditionsMethodName = preconditionsMethodName;
            this.preconditionsMethodDescriptor = preconditionsMethodDescriptor;
            this.exceptionName = exceptionName;
            this.exceptionConstructorDescriptor = exceptionConstructorDescriptor;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (preconditionsMethodName.equals(name) && preconditionsMethodDescriptor.equals(descriptor)) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (opcode == Opcodes.INVOKESPECIAL
                            && owner.equals("h08/preconditions/" + exceptionName)
                            && name.equals("<init>")
                            && descriptor.equals(exceptionConstructorDescriptor)) {
                            ParameterInterceptor interceptor = new ParameterInterceptor(this);
                            try {
                                interceptor.interceptParameters(Type.getArgumentTypes(descriptor));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
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
