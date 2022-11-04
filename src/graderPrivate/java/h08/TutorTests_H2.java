package h08;

import h08.preconditions.ArrayIsNullException;
import h08.preconditions.AtIndexException;
import h08.preconditions.AtIndexPairException;
import h08.preconditions.WrongNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    // DONE
    @Test
    @DisplayName("Konstruktor von \"ArrayIsNullException\" setzt die Botschaft korrekt.")
    public void arrayIsNullExceptionConstructorSetsMessageCorrectly() {
        var sut = assertDoesNotThrow(() -> new ArrayIsNullException(), "Der Konstruktor von ArrayIsNullException wirft eine " +
            "unerwartete Exception.");
        assertEquals("Array is null!", sut.getMessage(), "Die Botschaft der ArrayIsNullException wird nicht korrekt gesetzt.");
    }

    // DONE
    @Test
    @DisplayName("Klasse \"WrongNumberException\" wurde direkt von Exception abgeleitet.")
    public void wrongNumberExceptionExtendsRuntimeException() {
        assertSame(Exception.class, WrongNumberException.class.getSuperclass());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-4332, -32432, -1, 0, 1, 34.131, -44.112, Double.MIN_VALUE, Double.MAX_VALUE})
    @DisplayName("Konstruktor von \"WrongNumberException\" setzt die Botschaft korrekt.")
    public void wrongNumberExceptionConstructorSetsMessageCorrectly(double number) {
        var sut = assertDoesNotThrow(() -> new WrongNumberException(number),
            "Der Konstruktor von WrongNumberException wirft eine unerwartete Exception.");

        // This allows scientific notation (44e2), regular notation (4400) and leaving out leading zeros (.12)
        // A bit more tolerant towards different student solutions than comparing the strings directly
        // TODO: tolerate both point (.) and comma (,) as decimal separator
        var actualDoubleValue = Double.parseDouble(sut.getMessage());

        assertEquals(number, actualDoubleValue, "Die Botschaft der WrongNumberException wird nicht korrekt gesetzt.");
    }

    // DONE
    @Test
    @DisplayName("Klasse \"AtIndexException\" wurde direkt von RuntimeException abgeleitet.")
    public void atIndexExceptionExtendsRuntimeException() {
        assertSame(RuntimeException.class, AtIndexException.class.getSuperclass());
    }

    // DONE
    @ParameterizedTest
    @ValueSource(ints = {-4332, -32432, -1, 0, 1, 34131, -44112, Integer.MIN_VALUE, Integer.MAX_VALUE})
    @DisplayName("Konstruktor von \"AtIndexException\" setzt die Botschaft korrekt.")
    public void atIndexExceptionConstructorSetsMessageCorrectly(int number) {
        var sut = assertDoesNotThrow(() -> new AtIndexException(number),
            "Der Konstruktor von AtIndexException wirft eine unerwartete Exception.");
        assertEquals(String.format("Index: %s", number), sut.getMessage(),
            "Die Botschaft der AtIndexException wird nicht korrekt gesetzt.");
    }

    // DONE
    @Test
    @DisplayName("Klasse \"AtIndexPairException\" wurde direkt von Exception abgeleitet.")
    public void atIndexPairExceptionExtendsRuntimeException() {
        assertSame(Exception.class, AtIndexPairException.class.getSuperclass());
    }

    // DONE
    @ParameterizedTest
    @CsvSource({"-4332,-32432", "-1,0", "1,34131", "-44112,2141", "-2147483648,2147483647"})
    @DisplayName("Konstruktor von \"AtIndexPairException\" setzt die Botschaft korrekt.")
    public void atIndexPairExceptionConstructorSetsMessageCorrectly(int i, int j) {
        var sut = assertDoesNotThrow(() -> new AtIndexPairException(i, j),
            "Der Konstruktor von AtIndexPairException wirft eine unerwartete Exception.");
        assertEquals(String.format("Index: (%s,%s)", i, j), sut.getMessage(),
            "Die Botschaft der AtIndexPairException wird nicht korrekt gesetzt.");
    }
}
