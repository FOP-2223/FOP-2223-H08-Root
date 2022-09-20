package h08.calculation;

public class ArrayCalculatorWithRuntimeExceptions implements ArrayCalculator {
    /**
     * Adds up all double values in an array of arrays of double values. The double
     * values may not be negative or bigger than max.
     *
     * @param theArray The primary array containing the secondary arrays with their
     *                 double values.
     * @return double The sum of all double values contained on all secondary
     *         arrays.
     */
    @Override
    public double addUp(double[][] theArray, double max) {
        // Exceptions

        // 1
        if (theArray == null) {
            throw new NullPointerException("Primary array is void!");
        }

        // 2
        for (int i = 0; i < theArray.length; i++) {
            if (theArray[i] == null) {
                throw new NullPointerException("Secondary array at #" + i + " is void!");
            }
        }

        // 3
        if (max < 0) {
            throw new ArithmeticException("Upper bound is negative!");
        }

        // 4
        for (int i = 0; i < theArray.length; i++) {
            double[] secondaryArray = theArray[i];

            for (int j = 0; j < secondaryArray.length; j++) {
                double value = secondaryArray[j];

                if (value < 0 || value > max) {
                    throw new ArithmeticException("Value at (#" + i + ",#" + j + ") is not in range!");
                }
            }
        }

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
