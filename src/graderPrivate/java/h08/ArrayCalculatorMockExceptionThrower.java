package h08;

import h08.calculation.ArrayCalculator;

public class ArrayCalculatorMockExceptionThrower implements ArrayCalculator {
    private final Exception exceptionThrown;

    ArrayCalculatorMockExceptionThrower(Exception exceptionThrown) {
        this.exceptionThrown = exceptionThrown;
    }

    @Override
    public double addUp(double[][] theArray, double max) throws Exception {
        throw exceptionThrown;
    }
}
