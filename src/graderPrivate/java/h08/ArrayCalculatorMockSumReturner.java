package h08;

import h08.calculation.ArrayCalculator;

public class ArrayCalculatorMockSumReturner implements ArrayCalculator {
    private final double sumReturned;

    ArrayCalculatorMockSumReturner(double sumReturned) {
        this.sumReturned = sumReturned;
    }

    // 1. Summe falsch berechnet
    // 2. Wirft Exception, obwohl Eingabe in Ordnung
    // 3. Wirft falsche Exception bei fehlerhafter Eingabe
    // 3. b) falsche Nachricht der Exception
    // 4. Wirft keine Exception bei fehlerhafter Eingabe
    @Override
    public double addUp(double[][] theArray, double max) throws Exception {
        return sumReturned;
    }
}
