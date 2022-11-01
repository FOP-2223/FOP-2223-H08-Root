package h08;

import h08.calculation.ArrayCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestForSubmission
@DisplayName("H5.1")
public class TutorTests_H5_1 {
    @Test
    @DisplayName("Methode \"testSum\" wirft einen AssertionFailedError, wenn der ArrayCalculator die Summe nicht korrekt " +
        "berechnet.")
    public void testSumThrowsExceptionWhenSumNotCorrect() throws NoSuchMethodException, IllegalAccessException {
        var testSumMethod = CalculatorTests.class.getDeclaredMethod(
            "testSum", ArrayCalculator.class, double[][].class, double.class, double.class);
        testSumMethod.setAccessible(true);

        var sut = new CalculatorTests();
        var assertionThrown = false;

        try {
            testSumMethod.invoke(sut, new ArrayCalculatorMockSumReturner(435), new double[0][], 0, 111);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof AssertionError) {
                assertionThrown = true;
            }
        }

        assertTrue(assertionThrown, "Die Methode \"testSum\" wirft keinen AssertionFailedError, " +
            "wenn die Summe durch den ArrayCalculator falsch berechnet wurde.");
    }

    @Test
    @DisplayName("Methode \"testSum\" wirft einen AssertionFailedError, wenn der ArrayCalculator eine Exception wirft.")
    public void testSumThrowsExceptionWhenCalculatorThrowsException() throws NoSuchMethodException, InvocationTargetException
        , IllegalAccessException {
        var testSumMethod = CalculatorTests.class.getDeclaredMethod(
            "testSum", ArrayCalculator.class, double[][].class, double.class, double.class);
        testSumMethod.setAccessible(true);

        var sut = new CalculatorTests();
        var exceptionThrown = new MockException();
        var mockCalculator = new ArrayCalculatorMockExceptionThrower(exceptionThrown);

        var assertionThrown = false;

        try {
            testSumMethod.invoke(sut, mockCalculator, new double[0][], 0, 0);
        } catch (InvocationTargetException e) {
            var targetException = e.getTargetException();
            if (targetException instanceof AssertionError) {
                assertionThrown = true;
                assertEquals("Unexpected exception: pinguin", targetException.getMessage(),
                    "Der von der Methode \"testSum\" geworfene AssertionFailedError " +
                        "hat die falsche Botschaft.");
            }
        }

        assertTrue(assertionThrown, "Die Methode \"testSum\" wirft keinen AssertionFailedError, " +
            "wenn der ArrayCalculator eine Exception wirft.");
    }

    @Test
    @DisplayName("Methode \"testSum\" wirft keine Exception, wenn der ArrayCalculator die Summe korrekt berechnet.")
    public void testSumPassesWhenSumCorrect() throws NoSuchMethodException {
        var testSumMethod = CalculatorTests.class.getDeclaredMethod(
            "testSum", ArrayCalculator.class, double[][].class, double.class, double.class);
        testSumMethod.setAccessible(true);

        var sut = new CalculatorTests();
        final var expectedSum = 194;
        var mockCalculator = new ArrayCalculatorMockSumReturner(expectedSum);

        assertDoesNotThrow(() -> testSumMethod.invoke(sut, mockCalculator, new double[0][], 0, expectedSum),
            "Die Methode \"testSum\" wirft bei korrekter Berechnung der Summe eine unerwartete Exception.");
    }
}
