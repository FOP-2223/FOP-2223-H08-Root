package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.preconditions.Preconditions;
import h08.transform.ParameterCheckCT;
import h08.utils.ChildCollectionCriterionBuilder;
import h08.utils.CriterionBuilder;
import h08.utils.OnePointCriterionBuilder;
import h08.utils.RubricBuilder;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;
import org.sourcegrade.jagr.api.testing.TestCycle;

public class H08_RubricProvider implements RubricProvider {
    /*
     * Punktekriterien:
     * - H1.1 1 Punkt
     *      - Summenberechnung korrekt 1 Punkt
     * - H1.2 2 Punkte
     *      - je Fall 0,5 Punkte (4 Fälle insgesamt)
     *      - mehr als 4 throw-Anweisungen verwendet: -1 Punkt
     * - H2 4 Punkte
     *      - je korrekt implementierte Exception-Klasse 1 Punkt (4 Klassen insgesamt)
     * - H3.1 4 Punkte
     *      - je korrekt implementierter Methode in der Preconditions-Klasse 1 Punkt (4 Methoden insgesamt)
     * - H3.2 5 Punkte
     *      - Summenberechnung korrekt 1 Punkt
     *      - je korrekter Aufruf der Methode aus der Preconditions-Klasse 1 Punkt (4 Aufrufe insgesamt)
     *          - Aufruf muss an der richtigen Stelle (richtige Reihenfolge) erfolgen, damit es einen Punkt gibt
     * - H4 3 Punkte
     *      - bei keinem Ausnahmefall wird die Summe ausgegeben 1 Punkt
     *      - bei einer AtIndexException oder AtIndexPairException wird "Bad array: ..." ausgegeben 1 Punkt
     *      - Bei einer WrongNumberException wird "Bad max value: ..." ausgegeben 1 Punkt
     *      - zusätzliche throws-Klauseln: -1 Punkt
     * - H5.1 3 Punkte
     *      - Methode wirft AssertionError, wenn die Summe falsch berechnet wurde 1 Punkt
     *      - Methode wirft AssertionError mit korrekter Botschaft, wenn bei der Berechnung der Summe eine Exception geworfen
     * wird 1 Punkt
     *      - Methode wirft keinen AssertionError, wenn die Summe korrekt berechnet wird 1 Punkt
     * - H5.2 3 Punkte
     *      - Methode wirft AssertionError, wenn Calculator keine Exception wirft 1 Punkt
     *      - Methode wirft AssertionError, wenn der Datentyp der Exception nicht übereinstimmt 1 Punkt
     *      - Methode wirft AssertionError mit korrekter Botschaft, wenn die Botschaft der Exception nicht übereinstimmt 1 Punkt
     *      - Methode wirft AssertionError, obwohl Exception wie erwartet geworfen wird -1 Punkt
     */

    @Override
    public Rubric getRubric() {
        var H1_1_T1 = new OnePointCriterionBuilder("Die Methode [[[addUp]]] berechnet die Summe korrekt.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H1_1.class.getMethod("addUpCalculatesSumCorrectly", ArrayNode.class, double.class)));

        var H1_1 = new ChildCollectionCriterionBuilder("H1.1 | Berechnung der Summe", H1_1_T1);

        var H1_2_T1 = new OnePointCriterionBuilder("Die Methode [[[addUp]]] wirft eine korrekte NullPointerException, wenn der " +
            "Hauptarray [[[null]]] ist oder wenn ein oder mehrere Einzelarrays [[[null]]] sind.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H1_2.class.getMethod("addUpHandlesNullPrimaryArrayCorrectly", double.class)),
            JUnitTestRef.ofMethod(() ->
                TutorTests_H1_2.class.getMethod("addUpHandlesNullSecondaryArrayCorrectly", ArrayNode.class, double.class,
                    int.class)));

        var H1_2_T2 = new OnePointCriterionBuilder("Die Methode [[[addUp]]] wirft eine korrekte ArithmeticException, wenn " +
            "[[[max]]] " +
            "negativ ist oder wenn eine Komponente negativ oder größer als [[[max]]] ist.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H1_2.class.getMethod("addUpHandlesNegativeMaxCorrectly", ArrayNode.class, double.class)),
            JUnitTestRef.ofMethod(() ->
                TutorTests_H1_2.class.getMethod("addUpHandlesValuesOutOfRangeCorrectly", ArrayNode.class,
                    ExpectedIndexPair.class, double.class)));

        var H1_2_T3 = new CriterionBuilder("Anforderung verletzt: Die Methode [[[addUp]]] soll maximal 4 " +
            "[[[throw]]]-Anweisungen verwenden.") {
            @Override
            public Criterion build() {
                return Criterion.builder()
                    .shortDescription(codeTagify(shortDescription))
                    .grader(Grader.testAwareBuilder()
                        .requirePass(JUnitTestRef.ofMethod(() ->
                            TutorTests_H1_2.class.getMethod("addUpDoesNotExceedMaximumNumberOfThrowStatements", TestCycle.class)))
                        .pointsFailedMin()
                        .pointsPassedMax()
                        .build())
                    .minPoints(-1)
                    .maxPoints(0)
                    .build();
            }
        };

        var H1_2 = new ChildCollectionCriterionBuilder("H1.2 | Prüfen der Ausnahmefälle", H1_2_T1, H1_2_T2, H1_2_T3);

        // DONE
        var H1 = new ChildCollectionCriterionBuilder("H1 | Methode mit RuntimeExceptions", H1_1, H1_2);

        var H2_T1 = new OnePointCriterionBuilder("Die Klasse [[[ArrayIsNullException]]] wurde korrekt implementiert.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H2.class.getMethod("arrayIsNullExceptionExtendsRuntimeException")),
            JUnitTestRef.ofMethod(() ->
                TutorTests_H2.class.getMethod("arrayIsNullExceptionConstructorSetsMessageCorrectly")));

        var H2_T2 = new OnePointCriterionBuilder("Die Klasse [[[WrongNumberException]]] wurde korrekt implementiert.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H2.class.getMethod("wrongNumberExceptionExtendsRuntimeException")),
            JUnitTestRef.ofMethod(() ->
                TutorTests_H2.class.getMethod("wrongNumberExceptionConstructorSetsMessageCorrectly", double.class)));

        var H2_T3 = new OnePointCriterionBuilder("Die Klasse [[[AtIndexException]]] wurde korrekt implementiert.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H2.class.getMethod("atIndexExceptionExtendsRuntimeException")),
            JUnitTestRef.ofMethod(() ->
                TutorTests_H2.class.getMethod("atIndexExceptionConstructorSetsMessageCorrectly", int.class)));

        var H2_T4 = new OnePointCriterionBuilder("Die Klasse [[[AtIndexPairException]]] wurde korrekt implementiert.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H2.class.getMethod("atIndexPairExceptionExtendsRuntimeException")),
            JUnitTestRef.ofMethod(() ->
                TutorTests_H2.class.getMethod("atIndexPairExceptionConstructorSetsMessageCorrectly", int.class, int.class)));

        // DONE
        var H2 = new ChildCollectionCriterionBuilder("H2 | Eigene Exception-Klassen", H2_T1, H2_T2, H2_T3, H2_T4);

        var H3_1_T1 = new OnePointCriterionBuilder("Die Methode [[[checkPrimaryArrayNotNull]]] wirft keine " +
            "ArrayIsNullException, " +
            "wenn der Hauptarray nicht [[[null]]] ist.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_1.class.getMethod("checkPrimaryArrayNotNullHandlesRegularCaseCorrectly1", ArrayNode.class)));
        //var H3_1_T2 = new UngradedCriterionBuilder("Die Methode [[[checkSecondaryArraysNotNull]]] ist korrekt implementiert.");
        var H3_1_T3 = new OnePointCriterionBuilder("Die Methode [[[checkNumberNotNegative]]] " +
            "deklariert eine WrongNumberException mittels [[[throws]]]-Klausel.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_1.class.getMethod("checkNumberNotNegativeDeclaresThrowsClause", TestCycle.class)));
        var H3_1_T4 = new OnePointCriterionBuilder("Die Methode [[[checkValuesInRange]]] " +
            "deklariert eine AtIndexPairException mittels [[[throws]]]-Klausel.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_1.class.getMethod("checkValuesInRangeDeclaresThrowsClause", TestCycle.class)));

        var H3_1 = new ChildCollectionCriterionBuilder("H3.1 | Die Klasse [[[Preconditions]]]", H3_1_T1, H3_1_T3, H3_1_T4);

        var H3_2_T2 = new OnePointCriterionBuilder("Die Methode [[[addUp]]] verwendet die Preconditions-Klasse, um den ersten " +
            "Ausnahmefall abzuprüfen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("addUpHandlesFirstCaseCorrectly")));
        var H3_2_T3 = new OnePointCriterionBuilder("Die Methode [[[addUp]]] verwendet die Preconditions-Klasse, um den zweiten " +
            "Ausnahmefall abzuprüfen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("addUpHandlesSecondCaseCorrectly")));
        var H3_2_T4 = new OnePointCriterionBuilder("Die Methode [[[addUp]]] verwendet die Preconditions-Klasse, um den dritten " +
            "Ausnahmefall abzuprüfen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("addUpHandlesThirdCaseCorrectly")));
        var H3_2_T5 = new OnePointCriterionBuilder("Die Methode [[[addUp]]] verwendet die Preconditions-Klasse, um den vierten " +
            "Ausnahmefall abzuprüfen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("addUpHandlesFourthCaseCorrectly")));
        var H3_2_T6 = new OnePointCriterionBuilder("Die Methode [[[addUp]]] deklariert eine WrongNumberException und eine " +
            "AtIndexPairException mittels [[[throws]]]-Klausel.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("checkAddUpDeclaresThrowsClauses", TestCycle.class)));

        var H3_2 = new ChildCollectionCriterionBuilder("H3.2 | Verwendung des Preconditions-Frameworks",
            H3_2_T2, H3_2_T3, H3_2_T4, H3_2_T5, H3_2_T6);

        var H3 = new ChildCollectionCriterionBuilder("H3 | Eigenes Preconditions-Framework", H3_1, H3_2);

        var H4_T1 = new OnePointCriterionBuilder("Die Methode [[[print]]] gibt bei korrekter Eingabe die Summe aus.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H4.class.getMethod("printOutputsSumForCorrectParameters")));
        var H4_T2 = new OnePointCriterionBuilder("Die Methode gibt die korrekte Fehlernachricht aus, wenn eine " +
            "[[[AtIndexException]]] oder [[[AtIndexPairException]]] geworfen wird.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H4.class.getMethod("printOutputsBadArrayForAtIndexException")),
            JUnitTestRef.ofMethod(() ->
                TutorTests_H4.class.getMethod("printOutputsBadArrayForAtIndexPairException")));
        var H4_T3 = new OnePointCriterionBuilder("Die Methode gibt die korrekte Fehlernachricht aus, wenn eine " +
            "[[[WrongNumberException]]] geworfen wird.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H4.class.getMethod("printOutputsBadMaxValueForWrongNumberException")));
        var H4_T4 = new CriterionBuilder("Anforderung verletzt: Die Methode soll keine weiteren Ausnahmefälle abfangen.") {
            @Override
            public Criterion build() {
                return Criterion.builder()
                    .shortDescription(codeTagify(shortDescription))
                    .grader(Grader.testAwareBuilder()
                        .requirePass(JUnitTestRef.ofMethod(() ->
                            TutorTests_H4.class.getMethod("printDoesNotCatchOtherExceptions")))
                        .pointsFailedMin()
                        .pointsPassedMax()
                        .build())
                    .minPoints(-1)
                    .maxPoints(0)
                    .build();
            }
        };

        // DONE
        var H4 = new ChildCollectionCriterionBuilder("H4 | Print-Methode", H4_T1, H4_T2, H4_T3, H4_T4);

        var H5_1_T1 = new OnePointCriterionBuilder(
            "Die Methode [[[testSum]]] wirft einen AssertionError, wenn der ArrayCalculator die Summe nicht korrekt " +
                "berechnet.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumThrowsExceptionWhenSumNotCorrect")));
        var H5_1_T2 = new OnePointCriterionBuilder(
            "Die Methode [[[testSum]]] wirft einen AssertionError, wenn der ArrayCalculator eine Exception wirft.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumThrowsExceptionWhenCalculatorThrowsException")));
        var H5_1_T3 = new OnePointCriterionBuilder(
            "Die Methode [[[testSum]]] wirft keinen AssertionError, wenn der ArrayCalculator die Summe korrekt berechnet.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumPassesWhenSumCorrect")));

        var H5_1 = new ChildCollectionCriterionBuilder("H5.1 | Testen der Summenberechnung", H5_1_T1, H5_1_T2, H5_1_T3);

        var H5_2_T1 = new OnePointCriterionBuilder(
            "Die Methode [[[testException]]] wirft einen AssertionError, wenn der ArrayCalculator keine Exception wirft.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenCalculatorThrowsNoException")));
        var H5_2_T2 = new OnePointCriterionBuilder(
            "Die Methode [[[testException]]] wirft einen AssertionError, wenn der Datentyp der vom ArrayCalculator geworfenen " +
                "Exception nicht dem erwarteten entspricht.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenExceptionTypeIsWrong")));
        var H5_2_T3 = new OnePointCriterionBuilder(
            "Methode [[[testException]]] wirft einen AssertionError, wenn die Botschaft der vom ArrayCalculator geworfenen " +
                "Exception nicht der erwarteten Botschaft entspricht.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenMessageIsWrong")));
        var H5_2_T4 = new CriterionBuilder("Die Methode [[[testException]]] darf keinen Assertion   Error werfen, wenn der ArrayCalculator " +
            "eine Exception wie erwartet wirft.") {
            @Override
            public Criterion build() {
                return Criterion.builder()
                    .shortDescription(codeTagify(shortDescription))
                    .grader(Grader.testAwareBuilder()
                        .requirePass(JUnitTestRef.ofMethod(() ->
                            TutorTests_H5_2.class.getMethod("testExceptionPassesWhenExceptionIsThrownCorrectly")))
                        .pointsFailedMin()
                        .pointsPassedMax()
                        .build())
                    .minPoints(-1)
                    .maxPoints(0)
                    .build();
            }
        };

        var H5_2 = new ChildCollectionCriterionBuilder("H5.2 | Test der Ausnahmebehandlung", H5_2_T1, H5_2_T2, H5_2_T3, H5_2_T4);

        // DONE
        var H5 = new ChildCollectionCriterionBuilder("H5 | Tests mit JUnit", H5_1, H5_2);

        var rubricBuilder = new RubricBuilder("H08 | Excéptions – Gotta catch ’em all!", H1, H2, H3, H4, H5);
        return rubricBuilder.build();
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        RubricProvider.super.configure(configuration);
        configuration.addTransformer(ClassTransformer.replacement(MockPreconditions.class, Preconditions.class));
        configuration.addTransformer(new ParameterCheckCT(
            "checkSecondaryArraysNotNull",
            "([[D)V",
            "AtIndexException",
            "(I)V"));

        configuration.addTransformer(new ParameterCheckCT(
            "checkNumberNotNegative",
            "(D)V",
            "WrongNumberException",
            "(D)V"));

        configuration.addTransformer(new ParameterCheckCT(
            "checkValuesInRange",
            "([[DD)V",
            "AtIndexPairException",
            "(II)V"));
        configuration.addTransformer(new ArrayCalculatorCtorReplacer());
    }
}
