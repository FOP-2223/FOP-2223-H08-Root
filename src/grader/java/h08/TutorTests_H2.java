package h08;

import h08.preconditions.ArrayIsNullException;
import h08.preconditions.AtIndexException;
import h08.preconditions.AtIndexPairException;
import h08.preconditions.WrongNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestForSubmission
@DisplayName("H2")
public class TutorTests_H2 {
    // DONE
    @Test
    @DisplayName("Klasse \"ArrayIsNullException\" wurde direkt von RuntimeException abgeleitet.")
    public void arrayIsNullExceptionExtendsRuntimeException() {
        assertSame(RuntimeException.class, ArrayIsNullException.class.getSuperclass());
    }

    @Test
    @DisplayName("Konstruktor von \"ArrayIsNullException\" setzt die Botschaft korrekt.")
    public void arrayIsNullExceptionConstructorSetsMessageCorrectly() {
        var sut = new ArrayIsNullException();
        assertEquals("Array is null!", sut.getMessage(), "Die Botschaft der ArrayIsNullException wird nicht korrekt gesetzt.");
    }

    // DONE
    @Test
    @DisplayName("Klasse \"WrongNumberException\" wurde direkt von Exception abgeleitet.")
    public void wrongNumberExceptionExtendsRuntimeException() {
        assertSame(Exception.class, WrongNumberException.class.getSuperclass());
    }

    // DONE
    @Test
    @DisplayName("Klasse \"AtIndexException\" wurde direkt von RuntimeException abgeleitet.")
    public void atIndexExceptionExtendsRuntimeException() {
        assertSame(RuntimeException.class, AtIndexException.class.getSuperclass());
    }

    // DONE
    @Test
    @DisplayName("Klasse \"AtIndexPairException\" wurde direkt von Exception abgeleitet.")
    public void atIndexPairExceptionExtendsRuntimeException() {
        assertSame(Exception.class, AtIndexPairException.class.getSuperclass());
    }
}
