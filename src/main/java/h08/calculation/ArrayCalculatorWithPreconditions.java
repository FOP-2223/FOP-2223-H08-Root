package h08.calculation;

import h08.preconditions.AtIndexPairException;
import h08.preconditions.Preconditions;
import h08.preconditions.WrongNumberException;

public class ArrayCalculatorWithPreconditions implements ArrayCalculator {

    /**
     * Adds up all double values in an array of arrays of double values. The double
     * values may not be negative or bigger than max.
     *
     * @param theArray The primary array containing the secondary arrays with their
     *                 double values
     * @param max      The maximum value any double value contained in the arrays may
     *                 have
     * @return double The sum of all double values contained on all secondary
     * arrays.
     */
    @Override
    public double addUp(double[][] theArray, double max)
        throws WrongNumberException, AtIndexPairException {
        // Exceptions

        // 1
        Preconditions.checkPrimaryArrayNotNull(theArray);

        // 2
        Preconditions.checkSecondaryArraysNotNull(theArray);

        // 3
        Preconditions.checkNumberNotNegative(max);

        // 4
        Preconditions.checkValuesInRange(theArray, max);

        // Calculate sum
        double sum = 0;
        for (double[] secondaryArray : theArray) {
            for (double value : secondaryArray) {
                sum += value;
            }
        }

        return sum;
    }
}
