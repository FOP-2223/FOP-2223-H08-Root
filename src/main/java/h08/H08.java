package h08;

public class H08 {

    /**
     * Adds up all double values in an array of arrays of double values. The double
     * values may not be negative or bigger than max.
     * 
     * @param theArray The primary array containing the secondary arrays with their
     *                 double values.
     * @param max      The maximum value any double value contained in the arrays
     *                 may have.
     * @return double The sum of all double values contained on all secondary
     *         arrays.
     */
    public double addUpWithAsserts(double[][] theArray, double max) {
        // Asserts

        // 1
        assert theArray != null : "Primary array is void!";

        // 2
        for (int i = 0; i < theArray.length; i++) {
            assert theArray[i] != null : "Secondary array at #" + i + " is void!";
        }

        // 3
        assert max >= 0 : "Upper bound is negative!";

        // 4
        for (int i = 0; i < theArray.length; i++) {
            double[] secondaryArray = theArray[i];

            for (int j = 0; j < secondaryArray.length; j++) {
                double value = secondaryArray[j];

                assert value >= 0 && value <= max : "Value at (#" + i + ",#" + j + ") is not in range!";
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

    /**
     * Adds up all double values in an array of arrays of double values. The double
     * values may not be negative or bigger than max.
     * 
     * @param theArray The primary array containing the secondary arrays with their
     *                 double values.
     * @param max      The maximum value any double value contained in the arrays
     *                 may have.
     * @return double The sum of all double values contained on all secondary
     *         arrays.
     */
    public double addUpWithRuntimeExceptions(double[][] theArray, double max) {
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

    /**
     * Adds up all double values in an array of arrays of double values. The double
     * values may not be negative or bigger than max.
     * 
     * @param theArray The primary array containing the secondary arrays with their
     *                 double values.
     * @param max      The maximum value any double value contained in the arrays
     *                 may have.
     * @return double The sum of all double values contained on all secondary
     *         arrays.
     */
    public double addUpWithSelfDefinedExceptions1(double[][] theArray, double max)
            throws ArrayIsNullException, AtIndexException, WrongNumberException, AtIndexPairException {
        // Exceptions

        // 1
        if (theArray == null) {
            throw new ArrayIsNullException();
        }

        // 2
        for (int i = 0; i < theArray.length; i++) {
            if (theArray[i] == null) {
                throw new AtIndexException(i);
            }
        }

        // 3
        if (max < 0) {
            throw new WrongNumberException(max);
        }

        // 4
        for (int i = 0; i < theArray.length; i++) {
            double[] secondaryArray = theArray[i];

            for (int j = 0; j < secondaryArray.length; j++) {
                double value = secondaryArray[j];

                if (value < 0 || value > max) {
                    throw new AtIndexPairException(i, j);
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

    /**
     * Adds up all double values in an array of arrays of double values. The double
     * values may not be negative or bigger than max.
     * 
     * @param theArray The primary array containing the secondary arrays with their
     *                 double values.
     * @param max      The maximum value any double value contained in the arrays
     *                 may have.
     * @return double The sum of all double values contained on all secondary
     *         arrays.
     */
    public double addUpWithSelfDefinedExceptions2(double[][] theArray, double max)
            throws ArrayIsNullException, AtIndexException, WrongNumberException, AtIndexPairException {
        // Exceptions

        // Phase 1
        // 1
        ArrayIsNullException arrayIsNullException = new ArrayIsNullException();

        // 2
        AtIndexException[] atIndexExceptions = new AtIndexException[20];
        for (int i = 0; i < 20; i++) {
            atIndexExceptions[i] = new AtIndexException(i);
        }

        // 3
        WrongNumberException wrongNumberException = new WrongNumberException(max);

        // 4
        AtIndexPairException[][] atIndexPairExceptions = new AtIndexPairException[20][];
        for (int i = 0; i < 20; i++) {
            int secondaryArrayLength = 10;
            atIndexPairExceptions[i] = new AtIndexPairException[secondaryArrayLength];

            for (int j = 0; j < secondaryArrayLength; j++) {
                atIndexPairExceptions[i][j] = new AtIndexPairException(i, j);
            }
        }

        // Phase 2
        // 1
        if (theArray == null) {
            throw arrayIsNullException;
        }

        // 2
        for (int i = 0; i < theArray.length; i++) {
            if (theArray[i] == null) {
                throw atIndexExceptions[i];
            }
        }

        // 3
        if (max < 0) {
            throw wrongNumberException;
        }

        // 4
        for (int i = 0; i < theArray.length; i++) {
            double[] secondaryArray = theArray[i];

            for (int j = 0; j < secondaryArray.length; j++) {
                double value = secondaryArray[j];

                if (value < 0 || value > max) {
                    throw atIndexPairExceptions[i][j];
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
