package h08;

import h08.calculation.ArrayCalculatorWithPreconditions;
import h08.preconditions.AtIndexException;
import h08.preconditions.AtIndexPairException;
import h08.preconditions.WrongNumberException;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        var array1 = new double[][]{
            new double[]{42},
            null,
            new double[]{42}
        };
        print(array1, 50);

        var array2 = new double[][]{
            new double[]{1},
            new double[]{1, 2},
            new double[]{1, -2, 3},
            new double[]{1, 2, 3, 4}
        };
        print(array2, 10);

        var array3 = new double[][]{
            new double[]{5},
            new double[]{12, 42}
        };
        print(array3, -4);
        print(array3, 50);
        print(null, 0);
    }

    /**
     * Adds up all double values in an array of arrays of double values and prints
     * the sum to the console.
     * If an exception occurs during calculation, an error message will be printed
     * instead. The double
     * values may not be negative or bigger than max.
     *
     * @param theArray The array of arrays of double values to be added up.
     * @param max      The maximum value any double value may have.
     */
    public static void print(double[][] theArray, double max) {
        var calculator = new ArrayCalculatorWithPreconditions();

        try {
            System.out.println("Sum: " + calculator.addUp(theArray, max));
        } catch (AtIndexException | AtIndexPairException ex) {
            System.out.println("Bad array: " + ex.getMessage());
        } catch (WrongNumberException ex) {
            System.out.println("Bad max value: " + ex.getMessage());
        }
    }
}
