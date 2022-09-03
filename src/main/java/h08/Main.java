package h08;

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
        var testAddUpVariants = new TestAddUpVariants();
        System.out.println(testAddUpVariants.testAddUpWithAssertsRegularCalls());
        System.out.println(testAddUpVariants.testAddUpWithAssertsExceptionalCalls());
        System.out.println(testAddUpVariants.testAddUpWithRuntimeExceptionsRegularCalls());
        System.out.println(testAddUpVariants.testAddUpWithRuntimeExceptionsExceptionalCalls());
    }
}
