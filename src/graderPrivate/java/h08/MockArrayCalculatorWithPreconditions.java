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
        return 42;
    }
}
