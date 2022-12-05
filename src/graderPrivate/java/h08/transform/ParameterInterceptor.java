package h08.transform;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import static org.objectweb.asm.Opcodes.*;

/**
 * Class for intercepting parameters of method calls.
 */
public class ParameterInterceptor {

    private final MethodVisitor mv;

    /**
     * Constructs a new {@link ParameterInterceptor} with the supplied {@link MethodVisitor}.
     * @param mv the {@link MethodVisitor}
     */
    public ParameterInterceptor(MethodVisitor mv) {
        this.mv = mv;
    }

    /**
     * Intercept parameters of any method call. <br>
     * This method creates a new {@link Object} array and stores all parameters to the called method in it.
     * Primitive types are boxed and stored as their respective wrapper class.
     * After this method finishes, the array reference is at the very top of the operand stack.
     * The array reference <i>must</i> be removed before invoking the method, e.g. by storing it somewhere.
     * The elements after it are the original parameters in reverse order - as they would be right before the method invocation.
     * @param types the {@link Type} array reflecting the method's parameter types
     */
    public void interceptParameters(Type[] types) {
        mv.visitIntInsn(BIPUSH, types.length);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
        for (int i = types.length - 1; i >= 0; i--) {
            mv.visitInsn(DUP_X1);
            mv.visitInsn(SWAP);
            mv.visitIntInsn(BIPUSH, i);
            mv.visitInsn(SWAP);
            boxPrimitiveValue(types[i]);
            mv.visitInsn(AASTORE);
        }
        for (int i = 0; i < types.length; i++) {
            mv.visitInsn(DUP);
            mv.visitIntInsn(BIPUSH, i);
            mv.visitInsn(AALOAD);
            unboxPrimitiveValue(types[i]);
            mv.visitInsn(SWAP);
        }
    }

    /**
     * Helper method to wrap primitive types in their wrapper class.
     * Reference types are ignored.
     * @param type the type of the parameter
     */
    private void boxPrimitiveValue(Type type) {
        switch (type.getDescriptor()) {
            case "I" -> mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            case "D" -> mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
        }
    }

    /**
     * Helper method to cast the parameter to its original type and unbox primitive types, if necessary.
     * @param type the original type of the parameter
     */
    private void unboxPrimitiveValue(Type type) {
        switch (type.getDescriptor()) {
            case "I" -> {
                mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            }
            case "D" -> {
                mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
            }
            default -> mv.visitTypeInsn(CHECKCAST, type.getInternalName());
        }
    }

    /**
     * Stores the array reference using the given consumer.
     * This method needs to be called after intercepting the parameters and before invoking the original method.
     * Keep in mind that the array reference always has the type {@link Object}{@code []}.
     * @param consumer the consumer to use
     */
    public void storeArrayRef(Consumer<MethodVisitor> consumer) {
        consumer.accept(mv);
    }

    /**
     * Stores the array reference in the specified field.
     * This method needs to be called after intercepting the parameters and before invoking the original method.
     * Keep in mind that the array reference always has the type {@link Object}{@code []}.
     * @param owner the declaring class of the field
     * @param name  the name of the field
     */
    public void storeArrayRefInField(String owner, String name) {
        storeArrayRef(mv -> mv.visitFieldInsn(PUTSTATIC, owner, name, "[Ljava/lang/Object;"));
    }

    /**
     * Stores the array reference in the specified field.
     * This method needs to be called after intercepting the parameters and before invoking the original method.
     * Keep in mind that the array reference always has the type {@link Object}{@code []}.
     * @param field the field
     */
    public void storeArrayRefInField(Field field) {
        storeArrayRefInField(Type.getInternalName(field.getDeclaringClass()), field.getName());
    }

    /**
     * Adds the array reference to the specified list.
     * This method needs to be called after intercepting the parameters and before invoking the original method.
     * Keep in mind that the array reference always has the type {@link Object}{@code []}.
     * @param owner the declaring class of the field
     * @param name  the name of the field
     */
    public void storeArrayRefInList(String owner, String name) {
        storeArrayRef(mv -> {
            mv.visitFieldInsn(GETSTATIC, owner, name, "Ljava/util/List;");
            mv.visitInsn(SWAP);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(POP);
        });
    }

    /**
     * Add the array reference to the specified list.
     * This method needs to be called after intercepting the parameters and before invoking the original method.
     * Keep in mind that the array reference always has the type {@link Object}{@code []}.
     * @param field the field
     */
    public void storeArrayRefInList(Field field) {
        storeArrayRefInList(Type.getInternalName(field.getDeclaringClass()), field.getName());
    }
}
