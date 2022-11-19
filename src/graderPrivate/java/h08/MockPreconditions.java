package h08;

import h08.preconditions.AtIndexPairException;
import h08.preconditions.Preconditions;
import h08.preconditions.WrongNumberException;

import java.util.ArrayList;
import java.util.List;

public class MockPreconditions {
    public record CheckPrimaryArrayNotNullInvocation(double[][] primaryArray) {
    }

    public record CheckSecondaryArrayNotNullInvocation(double[][] primaryArray) {
    }

    public record CheckNumberNotNegativeInvocation(double number) {
    }

    public record CheckValuesInRangeInvocation(double[][] primaryArray, double max) {
    }

    public static final List<CheckPrimaryArrayNotNullInvocation> CheckPrimaryArrayNotNullInvocations = new ArrayList<>();
    public static final List<CheckSecondaryArrayNotNullInvocation> CheckSecondaryArrayNotNullInvocations = new ArrayList<>();
    public static final List<CheckNumberNotNegativeInvocation> CheckNumberNotNegativeInvocations = new ArrayList<>();
    public static final List<CheckValuesInRangeInvocation> CheckValuesInRangeInvocations = new ArrayList<>();

    public static boolean forwardInvocations = true;

    public static void reset() {
        CheckPrimaryArrayNotNullInvocations.clear();
        CheckSecondaryArrayNotNullInvocations.clear();
        CheckNumberNotNegativeInvocations.clear();
        CheckValuesInRangeInvocations.clear();
        forwardInvocations = true;
    }

    public static void checkPrimaryArrayNotNull(double[][] primaryArray) {
        CheckPrimaryArrayNotNullInvocations.add(new CheckPrimaryArrayNotNullInvocation(primaryArray));
        if (forwardInvocations) {
            Preconditions.checkPrimaryArrayNotNull(primaryArray);
        }
    }

    public static void checkSecondaryArraysNotNull(double[][] primaryArray) {
        CheckSecondaryArrayNotNullInvocations.add(new CheckSecondaryArrayNotNullInvocation(primaryArray));
        if (forwardInvocations) {
            Preconditions.checkSecondaryArraysNotNull(primaryArray);
        }
    }

    public static void checkNumberNotNegative(double number) throws WrongNumberException {
        CheckNumberNotNegativeInvocations.add(new CheckNumberNotNegativeInvocation(number));
        if (forwardInvocations) {
            Preconditions.checkNumberNotNegative(number);
        }
    }

    public static void checkValuesInRange(double[][] primaryArray, double max) throws AtIndexPairException {
        CheckValuesInRangeInvocations.add(new CheckValuesInRangeInvocation(primaryArray, max));
        if (forwardInvocations) {
            Preconditions.checkValuesInRange(primaryArray, max);
        }
    }
}
