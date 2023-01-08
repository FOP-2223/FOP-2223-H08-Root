package h08;

import h08.calculation.ArrayCalculatorWithPreconditions;
import h08.preconditions.ArrayIsNullException;
import h08.preconditions.AtIndexException;
import h08.preconditions.AtIndexPairException;
import h08.preconditions.WrongNumberException;

public class MockArrayCalculatorWithPreconditions extends ArrayCalculatorWithPreconditions {
    @Override
    public double addUp(double[][] theArray, double max) throws ArrayIsNullException, AtIndexException, WrongNumberException,
        AtIndexPairException {
        if (theArray == null) {
            throw new ArrayIsNullException();
        }

        for (int i = 0; i < theArray.length; i++) {
            if (theArray[i] == null) {
                throw new AtIndexException(i);
            }
        }

        if (max < 0) {
            throw new WrongNumberException(max);
        }

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
}
