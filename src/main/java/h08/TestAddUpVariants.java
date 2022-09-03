package h08;

import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAddUpVariants {
    boolean testAddUpWithAssertsRegularCalls() {
        H08 sut = new H08();
        boolean testSuccessful = true;

        // Aufruf 1
        try {
            double sum = sut.addUpWithAsserts(new double[0][], Integer.MAX_VALUE);
            if (sum != 0) {
                testSuccessful = false;
            }
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
            testSuccessful = false;
        }

        // Aufruf 2
        try {
            double[][] array = new double[1][];
            array[0] = new double[0];

            double sum = sut.addUpWithAsserts(array, Integer.MAX_VALUE);
            if (sum != 0) {
                testSuccessful = false;
            }
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
            testSuccessful = false;
        }

        // Aufruf 3
        try {
            double[][] array = new double[10][];
            var n = 0;
            for (int i = 0; i < array.length; i++) {
                array[i] = new double[10 + 3 * i];
                for (int j = 0; j < array[i].length; j++) {
                    n++;
                    array[i][j] = n;
                }
            }
            double expectedSum = (n * (n + 1)) / 2f;
            double sum = sut.addUpWithAsserts(array, Integer.MAX_VALUE);

            if (sum != expectedSum) {
                testSuccessful = false;
            }
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
            testSuccessful = false;
        }

        return testSuccessful;
    }

    boolean testAddUpWithAssertsExceptionalCalls() {
        H08 sut = new H08();
        boolean testSuccessful = true;

        // Fall 1
        try {
            sut.addUpWithAsserts(null, 0);
            testSuccessful = false;
        } catch (AssertionError error) {
            String actualMessage = error.getMessage();
            String expectedMessage = "Primary array is void!";
            if (!Objects.equals(actualMessage, expectedMessage)) {
                System.out.println(expectedMessage + " : " + actualMessage);
                testSuccessful = false;
            }
        }

        // Fall 2
        for (int i = 0; i < 10; i++) {
            double[][] primaryArray = new double[10][];

            for (int j = 0; j < primaryArray.length; j++) {
                if (j != i) {
                    primaryArray[j] = new double[10];
                }
            }

            try {
                sut.addUpWithAsserts(primaryArray, Integer.MAX_VALUE);
                testSuccessful = false;
            } catch (AssertionError error) {
                String actualMessage = error.getMessage();
                String expectedMessage = "Secondary array at #" + i + " is void!";
                if (!Objects.equals(actualMessage, expectedMessage)) {
                    System.out.println(expectedMessage + " : " + actualMessage);
                    testSuccessful = false;
                }
            }
        }

        // Fall 3
        try {
            sut.addUpWithAsserts(new double[0][], -1);
            testSuccessful = false;
        } catch (AssertionError error) {
            String actualMessage = error.getMessage();
            String expectedMessage = "Upper bound is negative!";
            if (!Objects.equals(actualMessage, expectedMessage)) {
                System.out.println(expectedMessage + " : " + actualMessage);
                testSuccessful = false;
            }
        }

        // Fall 4
        for (int a = 0; a < 2; a++) {
            for (int i = 0; i < 15; i++) {
                int count = 0;
                double[][] primaryArray = new double[5][];
                int primaryIndex = 0;
                int secondaryIndex = 0;

                for (int j = 0; j < primaryArray.length; j++) {
                    primaryArray[j] = new double[j + 1];
                    for (int k = 0; k < primaryArray[j].length; k++) {
                        if (count == i) {
                            primaryIndex = j;
                            secondaryIndex = k;
                            if (a == 0) {
                                primaryArray[j][k] = -1;
                            } else {
                                primaryArray[j][k] = 2;
                            }
                        } else {
                            primaryArray[j][k] = 0;
                        }
                        count++;
                    }
                }

                try {
                    sut.addUpWithAsserts(primaryArray, 1);
                    testSuccessful = false;
                } catch (AssertionError error) {
                    String actualMessage = error.getMessage();
                    String expectedMessage = "Value at (#" + primaryIndex + ",#" + secondaryIndex
                            + ") is not in range!";
                    if (!Objects.equals(actualMessage, expectedMessage)) {
                        System.out.println(expectedMessage + " : " + actualMessage);
                        testSuccessful = false;
                    }
                }
            }
        }

        return testSuccessful;
    }

    boolean testAddUpWithRuntimeExceptionsRegularCalls() {
        H08 sut = new H08();
        boolean testSuccessful = true;

        // Aufruf 1
        try {
            double sum = sut.addUpWithRuntimeExceptions(new double[0][], Integer.MAX_VALUE);
            if (sum != 0) {
                testSuccessful = false;
            }
        } catch (Exception exception) {
            System.out.println("Unexpected: " + exception.getMessage());
            testSuccessful = false;
        }

        // Aufruf 2
        try {
            double[][] array = new double[1][];
            array[0] = new double[0];

            double sum = sut.addUpWithRuntimeExceptions(array, Integer.MAX_VALUE);
            if (sum != 0) {
                testSuccessful = false;
            }
        } catch (Exception exception) {
            System.out.println("Unexpected: " + exception.getMessage());
            testSuccessful = false;
        }

        // Aufruf 3
        try {
            double[][] array = new double[10][];
            double expectedSum = 0;

            for (int i = 0; i < array.length; i++) {
                array[i] = new double[10 + 3 * i];
                for (int j = 0; j < array[i].length; j++) {
                    array[i][j] = j + 1f;
                }

                double currentArraySum = (array[i].length * (array[i].length + 1)) / 2f;
                expectedSum = expectedSum + currentArraySum;
            }

            double sum = sut.addUpWithRuntimeExceptions(array, Integer.MAX_VALUE);

            if (sum != expectedSum) {
                testSuccessful = false;
            }
        } catch (Exception exception) {
            System.out.println("Unexpected: " + exception.getMessage());
            testSuccessful = false;
        }

        return testSuccessful;
    }

    boolean testAddUpWithRuntimeExceptionsExceptionalCalls() {
        H08 sut = new H08();
        boolean testSuccessful = true;

        // Fall 1
        try {
            sut.addUpWithRuntimeExceptions(null, 0);
            testSuccessful = false;
        } catch (NullPointerException exception) {
            String actualMessage = exception.getMessage();
            String expectedMessage = "Primary array is void!";
            if (!Objects.equals(actualMessage, expectedMessage)) {
                System.out.println(expectedMessage + " : " + actualMessage);
                testSuccessful = false;
            }
        } catch (Exception exception) {
            System.out.println("Unexpected: " + exception.getMessage());
        }

        // Fall 2
        for (int i = 0; i < 10; i++) {
            double[][] primaryArray = new double[10][];

            for (int j = 0; j < primaryArray.length; j++) {
                if (j != i) {
                    primaryArray[j] = new double[10];
                }
            }

            try {
                sut.addUpWithRuntimeExceptions(primaryArray, Integer.MAX_VALUE);
                testSuccessful = false;
            } catch (NullPointerException exception) {
                String actualMessage = exception.getMessage();
                String expectedMessage = "Secondary array at #" + i + " is void!";
                if (!Objects.equals(actualMessage, expectedMessage)) {
                    System.out.println(expectedMessage + " : " + actualMessage);
                    testSuccessful = false;
                }
            } catch (Exception exception) {
                System.out.println("Unexpected: " + exception.getMessage());
            }
        }

        // Fall 3
        try {
            sut.addUpWithRuntimeExceptions(new double[0][], -1);
            testSuccessful = false;
        } catch (ArithmeticException exception) {
            String actualMessage = exception.getMessage();
            String expectedMessage = "Upper bound is negative!";
            if (!Objects.equals(actualMessage, expectedMessage)) {
                System.out.println(expectedMessage + " : " + actualMessage);
                testSuccessful = false;
            }
        } catch (Exception exception) {
            System.out.println("Unexpected: " + exception.getMessage());
        }

        // Fall 4
        for (int a = 0; a < 2; a++) {
            for (int i = 0; i < 15; i++) {
                int count = 0;
                double[][] primaryArray = new double[5][];
                int primaryIndex = 0;
                int secondaryIndex = 0;

                for (int j = 0; j < primaryArray.length; j++) {
                    primaryArray[j] = new double[j + 1];
                    for (int k = 0; k < primaryArray[j].length; k++) {
                        if (count == i) {
                            primaryIndex = j;
                            secondaryIndex = k;
                            if (a == 0) {
                                primaryArray[j][k] = -1;
                            } else {
                                primaryArray[j][k] = 2;
                            }
                        } else {
                            primaryArray[j][k] = 0;
                        }
                        count++;
                    }
                }

                try {
                    sut.addUpWithRuntimeExceptions(primaryArray, 1);
                    testSuccessful = false;
                } catch (ArithmeticException exception) {
                    String actualMessage = exception.getMessage();
                    String expectedMessage = "Value at (#" + primaryIndex + ",#" + secondaryIndex
                            + ") is not in range!";
                    if (!Objects.equals(actualMessage, expectedMessage)) {
                        System.out.println(expectedMessage + " : " + actualMessage);
                        testSuccessful = false;
                    }
                } catch (Exception exception) {
                    System.out.println("Unexpected: " + exception.getMessage());
                }
            }
        }

        return testSuccessful;
    }

    void testAddUpWithSelfDefinedExceptions1ExceptionalCalls() {
        H08 sut = new H08();

        // Fall 1
        assertThrows(ArrayIsNullException.class, () -> sut.addUpWithSelfDefinedExceptions1(null, 0));

        // Fall 2
        for (int i = 0; i < 10; i++) {
            double[][] primaryArray = new double[10][];

            for (int j = 0; j < primaryArray.length; j++) {
                if (j != i) {
                    primaryArray[j] = new double[10];
                }
            }

            assertThrows(AtIndexException.class,
                    () -> sut.addUpWithSelfDefinedExceptions1(primaryArray, Integer.MAX_VALUE));
        }

        // Fall 3
        assertThrows(WrongNumberException.class,
                () -> sut.addUpWithSelfDefinedExceptions1(new double[0][], -1));

        // Fall 4
        for (int a = 0; a < 2; a++) {
            for (int i = 0; i < 15; i++) {
                int count = 0;
                double[][] primaryArray = new double[5][];

                for (int j = 0; j < primaryArray.length; j++) {
                    primaryArray[j] = new double[j + 1];
                    for (int k = 0; k < primaryArray[j].length; k++) {
                        if (count == i) {
                            if (a == 0) {
                                primaryArray[j][k] = -1;
                            } else {
                                primaryArray[j][k] = 2;
                            }
                        } else {
                            primaryArray[j][k] = 0;
                        }
                        count++;
                    }
                }

                assertThrows(AtIndexPairException.class, () -> sut.addUpWithSelfDefinedExceptions1(primaryArray, 1));
            }
        }
    }
}
