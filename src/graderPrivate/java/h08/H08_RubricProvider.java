package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.preconditions.Preconditions;
import h08.utils.ChildCollectionCriterionBuilder;
import h08.utils.OnePointCriterionBuilder;
import h08.utils.RubricBuilder;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;
import org.sourcegrade.jagr.api.testing.TestCycle;

public class H08_RubricProvider implements RubricProvider {
//    public static final Criterion H2_T1 = Criterion.builder()
////        .shortDescription("Klasse RobotWithOffspring ist korrekt deklariert.")
//        .grader(
//            Grader.testAwareBuilder()
////                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod(
////                    "t01")))
//                .pointsPassedMax()
//                .pointsFailedMin()
//                .build())
//        .build();
//
//    public static final Criterion H2 = Criterion.builder()
//        .shortDescription("H2 | Eigene Exception-Klassen")
//        .addChildCriteria(H2_T1)
//        .build();

    @Override
    public Rubric getRubric() {
        var H1_1_T1 = new OnePointCriterionBuilder("Die Methode \"addUp\" berechnet die Summe korrekt.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H1_1.class.getMethod("addUpCalculatesSumCorrectly", ArrayNode.class, double.class)));

        var H1_1 = new ChildCollectionCriterionBuilder("H1.1 | Berechnung der Summe", H1_1_T1);

        var H1_2_T1 = new OnePointCriterionBuilder("Die Methode \"addUp\" verwendet maximal 4 throw-Anweisungen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H1_2.class.getMethod("addUpDoesNotExceedMaximumNumberOfThrowStatements", TestCycle.class)));

        var H1_2 = new ChildCollectionCriterionBuilder("H1.2 | Prüfen der Ausnahmefälle", H1_2_T1);

        var H1 = new ChildCollectionCriterionBuilder("H1 | Methode mit RuntimeExceptions", H1_1, H1_2);

        var H3_1_T1 = new OnePointCriterionBuilder("Die Methode \"checkPrimaryArrayNotNull\" wirft keine ArrayIsNullException, " +
            "wenn" +
            " der Hauptarray nicht null ist.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_1.class.getMethod("checkPrimaryArrayNotNullHandlesRegularCaseCorrectly1", ArrayNode.class)));

        var H3_1_T2 = new OnePointCriterionBuilder("Die Methode \"checkNumberNotNegative\" " +
            "deklariert eine WrongNumberException mittels throws-Klausel.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_1.class.getMethod("checkNumberNotNegativeDeclaresThrowsClause", TestCycle.class)));

        var H3_1_T3 = new OnePointCriterionBuilder("Die Methode \"checkValuesInRange\" " +
            "deklariert eine AtIndexException mittels throws-Klausel.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_1.class.getMethod("checkValuesInRangeDeclaresThrowsClause", TestCycle.class)));

        var H3_1 = new ChildCollectionCriterionBuilder("H3.1 | Die Klasse Preconditions", H3_1_T1, H3_1_T2, H3_1_T3);

        var H3_2_T2 = new OnePointCriterionBuilder("Die Methode \"addUp\" verwendet die Preconditions-Klasse, um den ersten " +
            "Ausnahmefall abzuprüfen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("addUpHandlesFirstCaseCorrectly")));

        var H3_2_T3 = new OnePointCriterionBuilder("Die Methode \"addUp\" verwendet die Preconditions-Klasse, um den zweiten " +
            "Ausnahmefall abzuprüfen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("addUpHandlesSecondCaseCorrectly")));

        var H3_2_T4 = new OnePointCriterionBuilder("Die Methode \"addUp\" verwendet die Preconditions-Klasse, um den dritten " +
            "Ausnahmefall abzuprüfen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("addUpHandlesThirdCaseCorrectly")));

        var H3_2_T5 = new OnePointCriterionBuilder("Die Methode \"addUp\" verwendet die Preconditions-Klasse, um den vierten " +
            "Ausnahmefall abzuprüfen.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H3_2.class.getMethod("addUpHandlesFourthCaseCorrectly")));

        var H3_2 = new ChildCollectionCriterionBuilder("H3.2 | Verwendung des Preconditions-Frameworks",
            H3_2_T2, H3_2_T3, H3_2_T4, H3_2_T5);

        var H3 = new ChildCollectionCriterionBuilder("H3 | Eigenes Preconditions-Framework", H3_1, H3_2);

        var H4_T1 = new OnePointCriterionBuilder("Die Methode \"print\" gibt bei korrekter Eingabe die Summe aus.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H4.class.getMethod("printOutputsSumForCorrectParameters", TestCycle.class)));

        var H4 = new ChildCollectionCriterionBuilder("H4 | Print-Methode", H4_T1);

        var H5_1_T1 = new OnePointCriterionBuilder(
            "Die Methode \"testSum\" wirft einen AssertionError, wenn der ArrayCalculator die Summe nicht korrekt " +
                "berechnet.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumThrowsExceptionWhenSumNotCorrect")));
        var H5_1_T2 = new OnePointCriterionBuilder(
            "Die Methode \"testSum\" wirft einen AssertionError, wenn der ArrayCalculator eine Exception wirft.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumThrowsExceptionWhenCalculatorThrowsException")));
        var H5_1_T3 = new OnePointCriterionBuilder(
            "Die Methode \"testSum\" wirft keine Exception, wenn der ArrayCalculator die Summe korrekt berechnet.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumPassesWhenSumCorrect")));

        var H5_1 = new ChildCollectionCriterionBuilder("H5.1 | Testen der Summenberechnung", H5_1_T1, H5_1_T2, H5_1_T3);

        var H5_2_T1 = new OnePointCriterionBuilder(
            "Die Methode \"testException\" wirft einen AssertionError, wenn der ArrayCalculator keine Exception wirft oder " +
                "Datentyp der Exception nicht dem erwarteten entspricht.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenCalculatorThrowsNoException")),
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenExceptionTypeIsWrong")));
        var H5_2_T2 = new OnePointCriterionBuilder(
            "Methode \"testException\" wirft einen AssertionError, wenn die Botschaft der von ArrayCalculator geworfenen " +
                "Exception nicht der erwarteten Botschaft entspricht.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenMessageIsWrong")));
        var H5_2_T3 = new OnePointCriterionBuilder(
            "Methode \"testException\" wirft keinen Error, wenn der ArrayCalculator eine Exception wie erwartet wirft.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionPassesWhenExceptionIsThrownCorrectly")));

        var H5_2 = new ChildCollectionCriterionBuilder("H5.2 | Test der Ausnahmebehandlung", H5_2_T1, H5_2_T2, H5_2_T3);

        // H5 DONE
        var H5 = new ChildCollectionCriterionBuilder("H5 | Tests mit JUnit", H5_1, H5_2);

        var rubricBuilder = new RubricBuilder("H08 | Excéptions – Gotta catch ’em all!", H1, H3, H4, H5);
        return rubricBuilder.build();
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        RubricProvider.super.configure(configuration);
        configuration.addTransformer(ClassTransformer.replacement(MockPreconditions.class, Preconditions.class));
    }
}
