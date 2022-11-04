package h08;

import h08.calculation.ArrayCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

// DONE
@TestForSubmission
@DisplayName("H5.1")
public class TutorTests_H5_1 {
    // DONE
    @Test
    @DisplayName("Methode \"testSum\" wirft einen AssertionError, wenn der ArrayCalculator die Summe nicht korrekt " +
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

        assertTrue(assertionThrown, "Die Methode \"testSum\" wirft keinen AssertionError, " +
            "wenn die Summe durch den ArrayCalculator falsch berechnet wurde.");
    }

    // DONE
    @Test
    @DisplayName("Methode \"testSum\" wirft einen AssertionError, wenn der ArrayCalculator eine Exception wirft.")
    public void testSumThrowsExceptionWhenCalculatorThrowsException() throws NoSuchMethodException, IllegalAccessException {
        var testSumMethod = CalculatorTests.class.getDeclaredMethod(
            "testSum", ArrayCalculator.class, double[][].class, double.class, double.class);
        testSumMethod.setAccessible(true);

        var sut = new CalculatorTests();
        var mockCalculator = new ArrayCalculatorMockExceptionThrower(new Exception("pinguin"));

        var assertionThrown = false;

        try {
            testSumMethod.invoke(sut, mockCalculator, new double[0][], 0, 0);
        } catch (InvocationTargetException e) {
            var targetException = e.getTargetException();
            if (targetException instanceof AssertionError) {
                assertionThrown = true;
                assertEquals("Unexpected exception: pinguin", targetException.getMessage(),
                    "Der von der Methode \"testSum\" geworfene AssertionError " +
                        "hat die falsche Botschaft.");
            }
        }

        assertTrue(assertionThrown, "Die Methode \"testSum\" wirft keinen AssertionError, " +
            "wenn der ArrayCalculator eine Exception wirft.");
    }

    // DONE
    @Test
    @DisplayName("Methode \"testSum\" wirft keinen Error, wenn der ArrayCalculator die Summe korrekt berechnet.")
    public void testSumPassesWhenSumCorrect() throws NoSuchMethodException, IllegalAccessException {
        var testSumMethod = CalculatorTests.class.getDeclaredMethod(
            "testSum", ArrayCalculator.class, double[][].class, double.class, double.class);
        testSumMethod.setAccessible(true);

        var sut = new CalculatorTests();
        final var expectedSum = 194;
        var mockCalculator = new ArrayCalculatorMockSumReturner(expectedSum);

        try {
            testSumMethod.invoke(sut, mockCalculator, new double[0][], 0, expectedSum);
        } catch (InvocationTargetException e) {
            var targetException = e.getTargetException();
            fail(String.format(
                "Die Methode \"testSum\" wirft bei korrekter Berechnung der Summe eine unerwartete Exception: %s",
                targetException.getClass()), targetException);
        }
    }
}
